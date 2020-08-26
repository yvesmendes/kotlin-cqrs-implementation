package br.com.tdc.cqrs.catalog.dto

import java.math.BigDecimal

data class CatalogCartResponseItemDTO(val productId: String, val quantity : Int, val price : BigDecimal, val name : String)
