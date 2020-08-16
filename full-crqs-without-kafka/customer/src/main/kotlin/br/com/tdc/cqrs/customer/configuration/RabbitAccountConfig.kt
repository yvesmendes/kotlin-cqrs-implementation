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
class RabbitAccountConfig(private val connectionFactory: ConnectionFactory) {

    @PostConstruct
    fun createRabbitElements() {
        val rabbitAdmin = RabbitAdmin(connectionFactory)
        createExchange(rabbitAdmin)
        createCreditQueue(rabbitAdmin)
        createDebitQueue(rabbitAdmin)
        createBalanceUpdateQueue(rabbitAdmin)
        createInvalidQueue(rabbitAdmin)
        createDLQ(rabbitAdmin)
    }

    private fun createExchange(rabbitAdmin: RabbitAdmin) {
        val exchange = ExchangeBuilder
                .directExchange(QueueAccountDefinition.ACCOUNT_EXCHANGE)
                .durable(true)
                .build<Exchange>()
        rabbitAdmin.declareExchange(exchange)
    }

    private fun createCreditQueue(rabbitAdmin: RabbitAdmin) {
        val queue = QueueBuilder.durable(QueueAccountDefinition.ACCOUNT_QUEUE_ADD)
                .deadLetterExchange(QueueAccountDefinition.DLQ_EXCHANGE)
                .deadLetterRoutingKey(QueueAccountDefinition.DLQ_BINDING_KEY)
                .build()
        val binding = Binding(
                QueueAccountDefinition.ACCOUNT_QUEUE_ADD,
                Binding.DestinationType.QUEUE,
                QueueAccountDefinition.ACCOUNT_EXCHANGE,
                QueueAccountDefinition.ACCOUNT_QUEUE_CREDIT_KEY,
                null
        )
        rabbitAdmin.declareQueue(queue)
        rabbitAdmin.declareBinding(binding)
    }

    private fun createDebitQueue(rabbitAdmin: RabbitAdmin) {
        val queue = QueueBuilder.durable(QueueAccountDefinition.ACCOUNT_QUEUE_DEBIT)
                .maxLength(10)
                .ttl(300_000)
                .deadLetterExchange(QueueAccountDefinition.DLQ_EXCHANGE)
                .deadLetterRoutingKey(QueueAccountDefinition.DLQ_BINDING_KEY)
                .build()
        val binding = Binding(
                QueueAccountDefinition.ACCOUNT_QUEUE_DEBIT,
                Binding.DestinationType.QUEUE,
                QueueAccountDefinition.ACCOUNT_EXCHANGE,
                QueueAccountDefinition.ACCOUNT_QUEUE_DEBIT_KEY,
                null
        )
        rabbitAdmin.declareQueue(queue)
        rabbitAdmin.declareBinding(binding)
    }

    private fun createBalanceUpdateQueue(rabbitAdmin: RabbitAdmin) {
        val queue = QueueBuilder.durable(QueueAccountDefinition.ACCOUNT_QUEUE_PLACED)
                .maxLength(10)
                .ttl(300_000)
                .deadLetterExchange(QueueAccountDefinition.DLQ_EXCHANGE)
                .deadLetterRoutingKey(QueueAccountDefinition.DLQ_BINDING_KEY)
                .build()
        val binding = Binding(
                QueueAccountDefinition.ACCOUNT_QUEUE_PLACED,
                Binding.DestinationType.QUEUE,
                QueueAccountDefinition.ACCOUNT_EXCHANGE,
                QueueAccountDefinition.ACCOUNT_QUEUE_PLACED_KEY,
                null
        )
        rabbitAdmin.declareQueue(queue)
        rabbitAdmin.declareBinding(binding)
    }

    private fun createInvalidQueue(rabbitAdmin: RabbitAdmin) {
        val queue = QueueBuilder.durable(QueueAccountDefinition.ACCOUNT_QUEUE_INVALID)
                .maxLength(10)
                .ttl(300_000)
                .deadLetterExchange(QueueAccountDefinition.DLQ_EXCHANGE)
                .deadLetterRoutingKey(QueueAccountDefinition.DLQ_BINDING_KEY)
                .build()
        val binding = Binding(
                QueueAccountDefinition.ACCOUNT_QUEUE_INVALID,
                Binding.DestinationType.QUEUE,
                QueueAccountDefinition.ACCOUNT_EXCHANGE,
                QueueAccountDefinition.ACCOUNT_QUEUE_INVALID_KEY,
                null
        )
        rabbitAdmin.declareQueue(queue)
        rabbitAdmin.declareBinding(binding)
    }

    private fun createDLQ(rabbitAdmin: RabbitAdmin) {
        val queue = QueueBuilder.durable(QueueAccountDefinition.DLQ_QUEUE)
                .build()
        val exchange = ExchangeBuilder
                .directExchange(QueueAccountDefinition.DLQ_EXCHANGE)
                .durable(true)
                .build<Exchange>()
        val binding = Binding(
                QueueAccountDefinition.DLQ_QUEUE,
                Binding.DestinationType.QUEUE,
                QueueAccountDefinition.DLQ_EXCHANGE,
                QueueAccountDefinition.DLQ_BINDING_KEY,
                null
        )
        rabbitAdmin.declareQueue(queue)
        rabbitAdmin.declareExchange(exchange)
        rabbitAdmin.declareBinding(binding)
    }
}