package br.com.tdc.cqrs.order.services

import br.com.tdc.cqrs.order.client.CatalogFeignClient
import br.com.tdc.cqrs.order.configuration.QueueOrderDefinition
import br.com.tdc.cqrs.order.domain.Order
import br.com.tdc.cqrs.order.domain.OrderRedis
import br.com.tdc.cqrs.order.domain.Product
import br.com.tdc.cqrs.order.domain.ProductRedis
import br.com.tdc.cqrs.order.dto.CatalogCartDTO
import br.com.tdc.cqrs.order.dto.CatalogCartItemDTO
import br.com.tdc.cqrs.order.dto.CatalogCartResponseDTO
import br.com.tdc.cqrs.order.dto.ProductCartDTO
import br.com.tdc.cqrs.order.enums.OrderStatus
import br.com.tdc.cqrs.order.exceptions.NotFoundException
import br.com.tdc.cqrs.order.repository.OrderRedisRepository
import br.com.tdc.cqrs.order.repository.OrderRepository
import br.com.tdc.cqrs.order.repository.ProductQueryRepository
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.*

fun Iterable<BigDecimal>.sum(): BigDecimal {
    var sum: BigDecimal = BigDecimal.ZERO
    for (element in this) {
        sum += element
    }
    return sum
}

@Service
class OrderServiceImpl(private val orderRedisRepository: OrderRedisRepository,
                       private val productQueryRepository: ProductQueryRepository,
                       private val catalogFeignClient: CatalogFeignClient,
                       private val orderRepository: OrderRepository,
                        private val rabbitTemplate: RabbitTemplate) : OrderService {
    override fun addToCart(product: ProductCartDTO, customerId : Long) : OrderRedis {

        val productQuery = productQueryRepository.findById(product.productId).get()
        val productRedis = ProductRedis(productId = product.productId, quantity = product.quantity, name = productQuery.name, price = productQuery.price)

        var order = orderRedisRepository.findById(customerId).orElseGet {
            OrderRedis(id = customerId, orderId = UUID.randomUUID().toString(),total = BigDecimal.ZERO)
        }

        order.products.remove(productRedis)
        order.products.add(productRedis)

        order.total = order.products.map { it.price.multiply(BigDecimal(it.quantity)) }.sum()

        orderRedisRepository.save(order)

        return order
    }

    override fun calculate(customerId: Long): CatalogCartResponseDTO {
        var order = orderRedisRepository.findById(customerId).orElseThrow {
            NotFoundException()
        }

        var products = mutableListOf<CatalogCartItemDTO>()

        order.products.forEach {
            products.add(CatalogCartItemDTO(productId = it.productId, quantity = it.quantity))
        }

        var calculateOrderRequest = CatalogCartDTO(products=products)
        val catalogCartResponseDTO = catalogFeignClient.calculate(calculateOrderRequest)

        order.products = mutableListOf()
        order.products = mutableListOf()
        catalogCartResponseDTO.products.forEach {
            order.products.add(ProductRedis(productId = it.productId, name = it.name, price = it.price, quantity = it.quantity ))
        }

        orderRedisRepository.save(order)

        return catalogCartResponseDTO
    }

    override fun placeOrder(customerId: Long) {
        val orderCalculate = calculate(customerId)

        var products = mutableListOf<Product>()

        var order = orderRedisRepository.findById(customerId).get()

        orderCalculate.products.forEach {
            products.add(Product(productId = it.productId, quantity = it.quantity, price = it.price, name = it.name))
        }

        val finalOrder = Order(id = UUID.randomUUID().toString(),customerId = customerId,
                date = Date(), orderId = order.orderId, orderStatus = OrderStatus.OPEN, products = products)

        finalOrder.total = finalOrder.products.map { it.price.multiply(BigDecimal(it.quantity)) }.sum()

        orderRepository.save(finalOrder)
        rabbitTemplate.convertAndSend(QueueOrderDefinition.ORDER_EXCHANGE, QueueOrderDefinition.ORDER_QUEUE_KEY, finalOrder)
        orderRedisRepository.delete(order)
    }

    override fun findOrderById(orderId: String): Order {
        return orderRepository.findByOrderId(orderId)
    }
}
