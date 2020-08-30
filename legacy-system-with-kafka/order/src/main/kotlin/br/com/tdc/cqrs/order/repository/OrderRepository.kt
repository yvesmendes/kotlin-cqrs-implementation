package br.com.tdc.cqrs.order.repository


import br.com.tdc.cqrs.order.domain.Order
import org.springframework.data.mongodb.repository.MongoRepository

interface OrderRepository : MongoRepository<Order, String>{
    fun findByOrderId(productId : String) : Order
}
