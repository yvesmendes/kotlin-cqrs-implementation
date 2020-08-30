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
class RabbitCatalogConfig(private val connectionFactory: ConnectionFactory) {

    @PostConstruct
    fun createRabbitElements() {
        val rabbitAdmin = RabbitAdmin(connectionFactory)
        createExchange(rabbitAdmin)
        createFirstQueue(rabbitAdmin)
        createDLQ(rabbitAdmin)
        createPlacedQueue(rabbitAdmin)
        createOrderQueueInvalid(rabbitAdmin)
    }

    private fun createExchange(rabbitAdmin: RabbitAdmin) {
        val exchange = ExchangeBuilder
                .directExchange(QueueCatalogDefinition.CATALOG_EXCHANGE)
                .durable(true)
                .build<Exchange>()
        rabbitAdmin.declareExchange(exchange)
    }

    private fun createFirstQueue(rabbitAdmin: RabbitAdmin) {
        val queue = QueueBuilder.durable(QueueCatalogDefinition.CATALOG_QUEUE_ADD)
                .deadLetterExchange(QueueCatalogDefinition.DLQ_EXCHANGE)
                .deadLetterRoutingKey(QueueCatalogDefinition.DLQ_BINDING_KEY)
                .build()
        val binding = Binding(
                QueueCatalogDefinition.CATALOG_QUEUE_ADD,
                Binding.DestinationType.QUEUE,
                QueueCatalogDefinition.CATALOG_EXCHANGE,
                QueueCatalogDefinition.CATALOG_QUEUE_ADD_KEY,
                null
        )
        rabbitAdmin.declareQueue(queue)
        rabbitAdmin.declareBinding(binding)
    }

    private fun createPlacedQueue(rabbitAdmin: RabbitAdmin) {
        val queue = QueueBuilder.durable(QueueCatalogDefinition.CATALOG_QUEUE_PLACED)
                .deadLetterExchange(QueueCatalogDefinition.DLQ_EXCHANGE)
                .deadLetterRoutingKey(QueueCatalogDefinition.DLQ_BINDING_KEY)
                .build()
        val binding = Binding(
                QueueCatalogDefinition.CATALOG_QUEUE_PLACED,
                Binding.DestinationType.QUEUE,
                QueueCatalogDefinition.CATALOG_EXCHANGE,
                QueueCatalogDefinition.CATALOG_QUEUE_PLACED_KEY,
                null
        )
        rabbitAdmin.declareQueue(queue)
        rabbitAdmin.declareBinding(binding)
    }

    private fun createOrderQueueInvalid(rabbitAdmin: RabbitAdmin) {
        val queue = QueueBuilder.durable(QueueCatalogDefinition.ORDER_QUEUE_INVALID)
                .deadLetterExchange(QueueCatalogDefinition.DLQ_EXCHANGE)
                .deadLetterRoutingKey(QueueCatalogDefinition.DLQ_BINDING_KEY)
                .build()
        val binding = Binding(
                QueueCatalogDefinition.ORDER_QUEUE_INVALID,
                Binding.DestinationType.QUEUE,
                QueueCatalogDefinition.CATALOG_EXCHANGE,
                QueueCatalogDefinition.ORDER_QUEUE_INVALID_KEY,
                null
        )
        rabbitAdmin.declareQueue(queue)
        rabbitAdmin.declareBinding(binding)
    }


    private fun createDLQ(rabbitAdmin: RabbitAdmin) {
        val queue = QueueBuilder.durable(QueueCatalogDefinition.DLQ_QUEUE)
                .build()
        val exchange = ExchangeBuilder
                .directExchange(QueueCatalogDefinition.DLQ_EXCHANGE)
                .durable(true)
                .build<Exchange>()
        val binding = Binding(
                QueueCatalogDefinition.DLQ_QUEUE,
                Binding.DestinationType.QUEUE,
                QueueCatalogDefinition.DLQ_EXCHANGE,
                QueueCatalogDefinition.DLQ_BINDING_KEY,
                null
        )
        rabbitAdmin.declareQueue(queue)
        rabbitAdmin.declareExchange(exchange)
        rabbitAdmin.declareBinding(binding)
    }
}