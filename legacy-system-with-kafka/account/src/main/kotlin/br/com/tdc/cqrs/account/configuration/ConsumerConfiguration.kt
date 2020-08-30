package br.com.tdc.cqrs.account.configuration

import br.com.tdc.cqrs.account.listener.AddQueueListener
import br.com.tdc.cqrs.account.listener.CatalogQueueListener
import br.com.tdc.cqrs.account.listener.RemoveQueueListener
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
        private val queueListener: RemoveQueueListener,
        private val addQueueListener: AddQueueListener,
        private val orderPaidListener: CatalogQueueListener,
        private val simpleRabbitListenerContainerFactory: SimpleRabbitListenerContainerFactory
) {

    @Bean
    fun listenerContainer(): MessageListenerContainer {
        val container = SimpleMessageListenerContainer()
        container.connectionFactory = connectionFactory
        container.setQueueNames(QueueAccountDefinition.CUSTOMER_QUEUE_REMOVE)
        container.setMessageListener(queueListener)
        simpleRabbitListenerContainerFactory.adviceChain?.let {
            container.setAdviceChain(*it, retryPolicy())
        }
        return container
    }

    @Bean
    fun listenerContainerAdd(): MessageListenerContainer {
        val container = SimpleMessageListenerContainer()
        container.connectionFactory = connectionFactory
        container.setQueueNames(QueueAccountDefinition.CUSTOMER_QUEUE_ADD)
        container.setMessageListener(addQueueListener)
        simpleRabbitListenerContainerFactory.adviceChain?.let {
            container.setAdviceChain(*it, retryPolicy())
        }
        return container
    }


    @Bean
    fun listenerOrderPaid(): MessageListenerContainer {
        val container = SimpleMessageListenerContainer()
        container.connectionFactory = connectionFactory
        container.setQueueNames(QueueAccountDefinition.CATALOG_QUEUE_PLACED)
        container.setMessageListener(orderPaidListener)
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