package br.com.tdc.cqrs.order.dto


data class CatalogCartResponseDTO(var products: MutableList<CatalogCartResponseItemDTO>){
    constructor() : this(products= mutableListOf())
}
