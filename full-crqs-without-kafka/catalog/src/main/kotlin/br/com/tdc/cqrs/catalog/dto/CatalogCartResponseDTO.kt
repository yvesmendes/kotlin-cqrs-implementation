package br.com.tdc.cqrs.catalog.dto

import java.math.BigDecimal


data class CatalogCartResponseDTO(var products: MutableList<CatalogCartResponseItemDTO>, var total : BigDecimal)
