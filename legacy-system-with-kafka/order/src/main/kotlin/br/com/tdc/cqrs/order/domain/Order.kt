package br.com.tdc.cqrs.order.domain

import br.com.tdc.cqrs.order.enums.OrderStatus
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.util.*

@Document(collection = "orderColletion")
data class Order(@Id @JsonIgnore val id : String = "", val orderId : String = "", val date : Date,
                 val customerId: Long = -1, var products: List<Product>,
                 var total : BigDecimal = BigDecimal.ZERO,
                 var orderStatus: OrderStatus = OrderStatus.OPEN){
    constructor() : this(products = mutableListOf<Product>(), date =  Date())
}
