package br.com.tdc.cqrs.order.domain

import java.math.BigDecimal

data class ProductRedis(val productId : String){

    var name: String = ""
    var quantity : Int = 0
    var price : BigDecimal = BigDecimal.ZERO

    constructor(productId : String, name: String, quantity: Int, price : BigDecimal) : this(productId){
        this.name = name
        this.quantity = quantity
        this.price = price
    }
}
