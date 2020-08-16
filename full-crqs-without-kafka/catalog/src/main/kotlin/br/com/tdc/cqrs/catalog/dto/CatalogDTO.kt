package br.com.tdc.cqrs.catalog.dto

import java.math.BigDecimal


data class CatalogDTO(val name: String, val value: BigDecimal, val stock: Int)
