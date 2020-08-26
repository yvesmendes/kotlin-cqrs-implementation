package br.com.tdc.cqrs.order.listener

import br.com.tdc.cqrs.account.dto.BalanceRequestDTO
import br.com.tdc.cqrs.account.enums.BalanceRequestType
import br.com.tdc.cqrs.order.client.AccountFeignClient
import br.com.tdc.cqrs.order.configuration.QueueOrderDefinition
import br.com.tdc.cqrs.order.domain.Order
import br.com.tdc.cqrs.order.enums.OrderStatus
import br.com.tdc.cqrs.order.repository.OrderRepository
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.Message
import org.springframework.amqp.core.MessageListener
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.stereotype.Service

@Service
class ConfirmQueueListener(private val messageConverter: MessageConverter,
                           private val orderRepository: OrderRepository,
                           private val accountFeignClient: AccountFeignClient,
                           private val rabbitTemplate: RabbitTemplate) : MessageListener {
    private val log = LoggerFactory.getLogger(javaClass)

    override fun onMessage(message: Message?) {
        log.info("receive message from ${message?.messageProperties?.consumerQueue}")

        message?.let {
            val order: Order = messageConverter.fromMessage(it) as Order
            log.info("order: $order")

            val account = accountFeignClient.getByCustomer(order.customerId)

            try{
                accountFeignClient.balance(BalanceRequestDTO(amount = order.total, accountNumber = account.accountNumber, balanceRequestType = BalanceRequestType.DEBIT))
                var persistedOrder = orderRepository.findByOrderId(order.orderId)
                persistedOrder.orderStatus = OrderStatus.DONE
                orderRepository.save(persistedOrder)
            }catch (e : Exception){
                var persistedOrder = orderRepository.findByOrderId(order.orderId)
                persistedOrder.orderStatus = OrderStatus.INVALID
                orderRepository.save(persistedOrder)
                rabbitTemplate.convertAndSend(QueueOrderDefinition.ORDER_EXCHANGE, QueueOrderDefinition.ORDER_QUEUE_INVALID_KEY, order)
            }
        } ?: log.warn("No message found")

    }
}