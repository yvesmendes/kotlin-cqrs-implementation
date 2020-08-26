package br.com.tdc.cqrs.order.domain

import java.math.BigDecimal
import javax.persistence.Entity
import javax.persistence.Id


data class Product(val productId : String = "", val name: String, val price : BigDecimal = BigDecimal.ZERO, val quantity : Int = 0){
    constructor() : this(name = "")
}
