package br.com.tdc.cqrs.order.configuration

import br.com.tdc.cqrs.order.listener.CatalogQueueListener
import br.com.tdc.cqrs.order.listener.ConfirmQueueListener
import br.com.tdc.cqrs.order.listener.OrderRevertQueueListener
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
        private val catalogQueueListener: CatalogQueueListener,
        private val orderRevertQueueListener: OrderRevertQueueListener,
        private val confirmQueueListener: ConfirmQueueListener,
        private val simpleRabbitListenerContainerFactory: SimpleRabbitListenerContainerFactory
) {


    @Bean
    fun listenerContainerAdd(): MessageListenerContainer {
        val container = SimpleMessageListenerContainer()
        container.connectionFactory = connectionFactory
        container.setQueueNames(QueueOrderDefinition.CONSUMER_CATALOG_QUEUE)
        container.setMessageListener(catalogQueueListener)
        simpleRabbitListenerContainerFactory.adviceChain?.let {
            container.setAdviceChain(*it, retryPolicy())
        }
        return container
    }

    @Bean
    fun listenerContainerRevertOrder(): MessageListenerContainer {
        val container = SimpleMessageListenerContainer()
        container.connectionFactory = connectionFactory
        container.setQueueNames(QueueOrderDefinition.CONSUMER_ACCOUNT_REVERT_QUEUE)
        container.setMessageListener(orderRevertQueueListener)
        simpleRabbitListenerContainerFactory.adviceChain?.let {
            container.setAdviceChain(*it, retryPolicy())
        }
        return container
    }

    @Bean
    fun listenerContainerConfirmOrder(): MessageListenerContainer {
        val container = SimpleMessageListenerContainer()
        container.connectionFactory = connectionFactory
        container.setQueueNames(QueueOrderDefinition.CONSUMER_ACCOUNT_QUEUE_PLACED)
        container.setMessageListener(confirmQueueListener)
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