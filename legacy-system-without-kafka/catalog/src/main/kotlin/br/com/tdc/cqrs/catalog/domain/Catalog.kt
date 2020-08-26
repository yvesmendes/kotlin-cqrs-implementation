package br.com.tdc.cqrs.catalog.domain

import java.math.BigDecimal
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Catalog(@Id val productId : String = "", var name: String, var value : BigDecimal, var stock: Int){
    constructor() : this(name = "", value = BigDecimal.ZERO, stock = 0)
}
