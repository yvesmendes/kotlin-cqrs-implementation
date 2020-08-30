package br.com.tdc.cqrs.catalog.services

import br.com.tdc.cqrs.catalog.configuration.QueueCatalogDefinition
import br.com.tdc.cqrs.catalog.domain.Catalog
import br.com.tdc.cqrs.catalog.dto.*
import br.com.tdc.cqrs.catalog.enums.CatalogOperationType
import br.com.tdc.cqrs.catalog.exceptions.NotFoundException
import br.com.tdc.cqrs.catalog.repository.CatalogRepository
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.*

@Service
class CatalogServiceImpl(val catalogRepository : CatalogRepository, val rabbitTemplate: RabbitTemplate) : CatalogService {

    override fun updateProduct(product: Catalog) {
        val productPersisted = this.getById(product.productId)
        productPersisted.stock = product.stock
        productPersisted.value = product.value
        productPersisted.name = product.name
        catalogRepository.save(productPersisted)
        val catalogMessageDTO = CatalogMessageDTO(productId= productPersisted.productId, name = productPersisted.name,
                value = productPersisted.value, operation = CatalogOperationType.UPDATE.operation)
        sendMessage(QueueCatalogDefinition.CATALOG_QUEUE_ADD_KEY, catalogMessageDTO)
    }

    override fun calculateCatalog(catalogItem: CatalogCartDTO): CatalogCartResponseDTO {
        var catalogCartResponseDTO = CatalogCartResponseDTO(mutableListOf(), BigDecimal.ZERO)

        catalogItem.products.forEach {
            val productPersisted = catalogRepository.findById(it.productId).get()

            if(it.quantity <= productPersisted.stock){
                val catalogCartResponseItemDTO = CatalogCartResponseItemDTO(productId = productPersisted.productId,
                        price = productPersisted.value, quantity = it.quantity, name = productPersisted.name)
                catalogCartResponseDTO.products.add(catalogCartResponseItemDTO)
            }
        }

        return catalogCartResponseDTO
    }

    override fun getById(id: String): Catalog {
        return catalogRepository.findById(id).orElseThrow { NotFoundException() }
    }

    override fun deleteById(id: String) {
        val product = this.getById(id)
        catalogRepository.delete(product)
        val catalogMessageDTO = CatalogMessageDTO(productId= product.productId, name = product.name, value = product.value,
                operation = CatalogOperationType.DELETE.operation)
        sendMessage(QueueCatalogDefinition.CATALOG_QUEUE_ADD_KEY, catalogMessageDTO)
    }

    override fun saveCatalog(catalogDTO: CatalogDTO): Catalog {
        val product = Catalog(productId = UUID.randomUUID().toString(), name = catalogDTO.name, value = catalogDTO.value, stock = catalogDTO.stock)
        val persistedCustomer = catalogRepository.save(product)
        val catalogMessageDTO = CatalogMessageDTO(productId= persistedCustomer.productId, name = persistedCustomer.name,
                value = persistedCustomer.value, operation = CatalogOperationType.CREATE.operation)
        sendMessage(QueueCatalogDefinition.CATALOG_QUEUE_ADD_KEY, catalogMessageDTO)
        return persistedCustomer
    }

    private fun sendMessage(key : String, catalogMessageDTO : CatalogMessageDTO){
        rabbitTemplate.convertAndSend(QueueCatalogDefinition.CATALOG_EXCHANGE, key, catalogMessageDTO)
    }
}