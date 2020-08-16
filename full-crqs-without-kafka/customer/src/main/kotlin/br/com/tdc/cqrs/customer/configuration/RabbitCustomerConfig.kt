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
class RabbitCustomerConfig(private val connectionFactory: ConnectionFactory) {

    @PostConstruct
    fun createRabbitElements() {
        val rabbitAdmin = RabbitAdmin(connectionFactory)
        createCustomerExchange(rabbitAdmin)
        createFirstQueue(rabbitAdmin)
        createSecondQueue(rabbitAdmin)
        createDLQ(rabbitAdmin)
    }

    private fun createCustomerExchange(rabbitAdmin: RabbitAdmin) {
        val exchange = ExchangeBuilder
                .directExchange(QueueDefinition.CUSTOMER_EXCHANGE)
                .durable(true)
                .build<Exchange>()
        rabbitAdmin.declareExchange(exchange)
    }

    private fun createFirstQueue(rabbitAdmin: RabbitAdmin) {
        val queue = QueueBuilder.durable(QueueDefinition.CUSTOMER_QUEUE_ADD)
                .deadLetterExchange(QueueDefinition.DLQ_EXCHANGE)
                .deadLetterRoutingKey(QueueDefinition.DLQ_BINDING_KEY)
                .build()
        val binding = Binding(
                QueueDefinition.CUSTOMER_QUEUE_ADD,
                Binding.DestinationType.QUEUE,
                QueueDefinition.CUSTOMER_EXCHANGE,
                QueueDefinition.CUSTOMER_QUEUE_ADD_KEY,
                null
        )
        rabbitAdmin.declareQueue(queue)
        rabbitAdmin.declareBinding(binding)
    }

    private fun createSecondQueue(rabbitAdmin: RabbitAdmin) {
        val queue = QueueBuilder.durable(QueueDefinition.CUSTOMER_QUEUE_REMOVE)
                .maxLength(10)
                .ttl(300_000)
                .deadLetterExchange(QueueDefinition.DLQ_EXCHANGE)
                .deadLetterRoutingKey(QueueDefinition.DLQ_BINDING_KEY)
                .build()
        val binding = Binding(
                QueueDefinition.CUSTOMER_QUEUE_REMOVE,
                Binding.DestinationType.QUEUE,
                QueueDefinition.CUSTOMER_EXCHANGE,
                QueueDefinition.CUSTOMER_QUEUE_REMOVE_KEY,
                null
        )
        rabbitAdmin.declareQueue(queue)
        rabbitAdmin.declareBinding(binding)
    }

    private fun createDLQ(rabbitAdmin: RabbitAdmin) {
        val queue = QueueBuilder.durable(QueueDefinition.DLQ_QUEUE)
                .build()
        val exchange = ExchangeBuilder
                .directExchange(QueueDefinition.DLQ_EXCHANGE)
                .durable(true)
                .build<Exchange>()
        val binding = Binding(
                QueueDefinition.DLQ_QUEUE,
                Binding.DestinationType.QUEUE,
                QueueDefinition.DLQ_EXCHANGE,
                QueueDefinition.DLQ_BINDING_KEY,
                null
        )
        rabbitAdmin.declareQueue(queue)
        rabbitAdmin.declareExchange(exchange)
        rabbitAdmin.declareBinding(binding)
    }
}