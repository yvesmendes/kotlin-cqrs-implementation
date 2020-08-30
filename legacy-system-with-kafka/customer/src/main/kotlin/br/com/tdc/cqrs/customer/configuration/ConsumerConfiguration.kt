package br.com.tdc.cqrs.customer.configuration

import br.com.tdc.cqrs.customer.listener.BalanceQueueListener
import org.aopalliance.aop.Advice
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.listener.MessageListenerContainer
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class ConsumerConfiguration(
        private val connectionFactory: ConnectionFactory,
        private val queueListener: BalanceQueueListener,
        private val simpleRabbitListenerContainerFactory: SimpleRabbitListenerContainerFactory
) {

    @Bean
    fun listenerContainer(): MessageListenerContainer {
        val container = SimpleMessageListenerContainer()
        container.connectionFactory = connectionFactory
        container.setQueueNames(QueueDefinition.CONSUMER_ACCOUNT_QUEUE)
        container.setMessageListener(queueListener)
        simpleRabbitListenerContainerFactory.adviceChain?.let {
            container.setAdviceChain(*it, retryPolicy())
        }
        return container
    }

    @Bean
    fun listenerContainerDebit(): MessageListenerContainer {
        val container = SimpleMessageListenerContainer()
        container.connectionFactory = connectionFactory
        container.setQueueNames(QueueDefinition.CONSUMER_ACCOUNT_QUEUE_DEBIT)
        container.setMessageListener(queueListener)
        simpleRabbitListenerContainerFactory.adviceChain?.let {
            container.setAdviceChain(*it, retryPolicy())
        }
        return container
    }

    private fun retryPolicy(): Advice {
        return RetryInterceptorBuilder
                .stateless()
                .maxAttempts(5)
                .backOffOptions(
                        1000, // Initial interval
                        2.0, // Multiplier
                        6000 // Max interval
                )
                .build()
    }
}