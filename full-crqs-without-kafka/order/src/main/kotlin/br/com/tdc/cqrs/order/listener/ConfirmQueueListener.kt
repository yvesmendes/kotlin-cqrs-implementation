package br.com.tdc.cqrs.order.listener

import br.com.tdc.cqrs.order.domain.Order
import br.com.tdc.cqrs.order.enums.OrderStatus
import br.com.tdc.cqrs.order.repository.OrderRepository
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.Message
import org.springframework.amqp.core.MessageListener
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.stereotype.Service

@Service
class ConfirmQueueListener(private val messageConverter: MessageConverter,
                           private val orderRepository: OrderRepository) : MessageListener {
    private val log = LoggerFactory.getLogger(javaClass)

    override fun onMessage(message: Message?) {
        log.info("receive message from ${message?.messageProperties?.consumerQueue}")

        message?.let {
            val order: Order = messageConverter.fromMessage(it) as Order
            log.info("order: $order")

            var persistedOrder = orderRepository.findByOrderId(order.orderId)
            persistedOrder.orderStatus = OrderStatus.DONE
            orderRepository.save(persistedOrder)

        } ?: log.warn("No message found")

    }
}