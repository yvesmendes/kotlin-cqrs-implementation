package br.com.tdc.cqrs.catalog.dto

import java.math.BigDecimal


data class CatalogMessageDTO(val productId: String, val name: String, val value : BigDecimal, val operation : String)
