package br.com.tdc.cqrs.order.listener

import br.com.tdc.cqrs.catalog.dto.CatalogMessageDTO
import br.com.tdc.cqrs.order.domain.ProductQuery
import br.com.tdc.cqrs.order.repository.ProductQueryRepository
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.Message
import org.springframework.amqp.core.MessageListener
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.stereotype.Service

@Service
class CatalogQueueListener(private val messageConverter: MessageConverter, private val productQueryRepository: ProductQueryRepository) : MessageListener {
    private val log = LoggerFactory.getLogger(javaClass)

    override fun onMessage(message: Message?) {
        log.info("receive message from ${message?.messageProperties?.consumerQueue}")
        message?.let {
            val catalogMessageDTO: CatalogMessageDTO = messageConverter.fromMessage(it) as CatalogMessageDTO
            log.info("product: $catalogMessageDTO")

            when(catalogMessageDTO.operation){
                "C" -> {
                    val product = ProductQuery(id = catalogMessageDTO.productId, name = catalogMessageDTO.name, price = catalogMessageDTO.value)
                    productQueryRepository.save(product)
                }
                "U" ->{
                    var product = productQueryRepository.findById(catalogMessageDTO.productId).get()
                    product.price = catalogMessageDTO.value
                    product.name = catalogMessageDTO.name
                    productQueryRepository.save(product)
                }
                else -> {
                    val product = productQueryRepository.findById(catalogMessageDTO.productId).get()
                    productQueryRepository.delete(product)
                }
            }
        } ?: log.warn("No message found")

    }
}