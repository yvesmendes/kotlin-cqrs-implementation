package br.com.tdc.cqrs.catalog.listener

import br.com.tdc.cqrs.catalog.configuration.QueueCatalogDefinition
import br.com.tdc.cqrs.catalog.repository.CatalogRepository
import br.com.tdc.cqrs.order.domain.Order
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.Message
import org.springframework.amqp.core.MessageListener
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.stereotype.Service

@Service
class OrderQueueListener(private val messageConverter: MessageConverter,
                         private val catalogRepository: CatalogRepository,
                         private val rabbitTemplate: RabbitTemplate) : MessageListener {
    private val log = LoggerFactory.getLogger(javaClass)

    override fun onMessage(message: Message?) {
        log.info("receive message from ${message?.messageProperties?.consumerQueue}")
        message?.let { it ->
            val order: Order = messageConverter.fromMessage(it) as Order
            log.info("order: $order")

            order.products.forEach {
                var product = catalogRepository.findById(it.productId).get()
                product.stock -= it.quantity
                catalogRepository.save(product)
            }

            rabbitTemplate.convertAndSend(QueueCatalogDefinition.CATALOG_EXCHANGE, QueueCatalogDefinition.CATALOG_QUEUE_PLACED_KEY, order)
        } ?: log.warn("No message found")

    }
}