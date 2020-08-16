package br.com.tdc.cqrs.order.services

import br.com.tdc.cqrs.order.domain.Order
import br.com.tdc.cqrs.order.domain.OrderRedis
import br.com.tdc.cqrs.order.dto.CatalogCartResponseDTO
import br.com.tdc.cqrs.order.dto.ProductCartDTO

interface OrderService {
    fun addToCart(product: ProductCartDTO, customerId : Long): OrderRedis
    fun calculate(customerId: Long): CatalogCartResponseDTO
    fun placeOrder(customerId: Long)
    fun findOrderById(orderId: String): Order
}