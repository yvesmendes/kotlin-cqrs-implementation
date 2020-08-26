package br.com.tdc.cqrs.catalog.services

import br.com.tdc.cqrs.catalog.domain.Catalog
import br.com.tdc.cqrs.catalog.dto.CatalogCartDTO
import br.com.tdc.cqrs.catalog.dto.CatalogCartResponseDTO
import br.com.tdc.cqrs.catalog.dto.CatalogDTO

interface CatalogService {

    fun saveCatalog(customerDTO : CatalogDTO) : Catalog
    fun deleteById(id: String)
    fun getById(id: String): Catalog
    fun updateProduct(customer: Catalog)
    fun calculateCatalog(catalogItem: CatalogCartDTO): CatalogCartResponseDTO
}