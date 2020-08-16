package br.com.tdc.cqrs.customer.configuration

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.Exchange
import org.springframework.amqp.core.ExchangeBuilder
import org.springframework.amqp.core.QueueBuilder
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.context.annotation.Configuration
import javax.annotation.PostConstruct

@Configuration
class RabbitOrderConfig(private val connectionFactory: ConnectionFactory) {

    @PostConstruct
    fun createRabbitElements() {
        val rabbitAdmin = RabbitAdmin(connectionFactory)
        createExchange(rabbitAdmin)
        createOrderQueuePaid(rabbitAdmin)
        createOrderQueueAdd(rabbitAdmin)
        createDLQ(rabbitAdmin)
    }

    private fun createExchange(rabbitAdmin: RabbitAdmin) {
        val exchange = ExchangeBuilder
                .directExchange(QueueOrderDefinition.ORDER_EXCHANGE)
                .durable(true)
                .build<Exchange>()
        rabbitAdmin.declareExchange(exchange)
    }

    private fun createOrderQueueAdd(rabbitAdmin: RabbitAdmin) {
        val queue = QueueBuilder.durable(QueueOrderDefinition.ORDER_QUEUE_ADD)
                .deadLetterExchange(QueueOrderDefinition.DLQ_EXCHANGE)
                .deadLetterRoutingKey(QueueOrderDefinition.DLQ_BINDING_KEY)
                .build()
        val binding = Binding(
                QueueOrderDefinition.ORDER_QUEUE_ADD,
                Binding.DestinationType.QUEUE,
                QueueOrderDefinition.ORDER_EXCHANGE,
                QueueOrderDefinition.ORDER_QUEUE_KEY,
                null
        )
        rabbitAdmin.declareQueue(queue)
        rabbitAdmin.declareBinding(binding)
    }

    private fun createOrderQueuePaid(rabbitAdmin: RabbitAdmin) {
        val queue = QueueBuilder.durable(QueueOrderDefinition.ORDER_QUEUE_PAID)
                .deadLetterExchange(QueueOrderDefinition.DLQ_EXCHANGE)
                .deadLetterRoutingKey(QueueOrderDefinition.DLQ_BINDING_KEY)
                .build()
        val binding = Binding(
                QueueOrderDefinition.ORDER_QUEUE_PAID,
                Binding.DestinationType.QUEUE,
                QueueOrderDefinition.ORDER_EXCHANGE,
                QueueOrderDefinition.ORDER_QUEUE_KEY,
                null
        )
        rabbitAdmin.declareQueue(queue)
        rabbitAdmin.declareBinding(binding)
    }


    private fun createDLQ(rabbitAdmin: RabbitAdmin) {
        val queue = QueueBuilder.durable(QueueOrderDefinition.DLQ_QUEUE)
                .build()
        val exchange = ExchangeBuilder
                .directExchange(QueueOrderDefinition.DLQ_EXCHANGE)
                .durable(true)
                .build<Exchange>()
        val binding = Binding(
                QueueOrderDefinition.DLQ_QUEUE,
                Binding.DestinationType.QUEUE,
                QueueOrderDefinition.DLQ_EXCHANGE,
                QueueOrderDefinition.DLQ_BINDING_KEY,
                null
        )
        rabbitAdmin.declareQueue(queue)
        rabbitAdmin.declareExchange(exchange)
        rabbitAdmin.declareBinding(binding)
    }
}