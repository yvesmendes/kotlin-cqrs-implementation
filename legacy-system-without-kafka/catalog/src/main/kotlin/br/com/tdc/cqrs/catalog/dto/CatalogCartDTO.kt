package br.com.tdc.cqrs.catalog.dto


data class CatalogCartDTO(val products: List<CatalogCartItemDTO>) {
    constructor() : this(products =mutableListOf<CatalogCartItemDTO>())
}

