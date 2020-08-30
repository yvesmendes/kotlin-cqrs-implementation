package br.com.tdc.cqrs.order.controller

import br.com.tdc.cqrs.order.domain.Order
import br.com.tdc.cqrs.order.domain.OrderRedis
import br.com.tdc.cqrs.order.dto.CalculateOrderRequest
import br.com.tdc.cqrs.order.dto.CatalogCartResponseDTO
import br.com.tdc.cqrs.order.dto.ProductCartDTO
import br.com.tdc.cqrs.order.services.OrderService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("v1/orders")
class OrderController(val orderService: OrderService) {

    @PostMapping("/calculate")
    fun placeOrder(@RequestHeader(value="customerId") customerId : Long): ResponseEntity<CatalogCartResponseDTO> {
        return ResponseEntity.ok(orderService.calculate(customerId))
    }

    @PostMapping("/cart")
    fun addToCart(@RequestBody product : ProductCartDTO, @RequestHeader(value="customerId") customerId : Long): ResponseEntity<OrderRedis> {
        return ResponseEntity.ok(orderService.addToCart(product, customerId))
    }

    @PostMapping("/confirm")
    fun confirmOrder(@RequestHeader(value="customerId") customerId : Long): ResponseEntity<Unit> {
        orderService.placeOrder(customerId)
        return ResponseEntity.ok().build()
    }

    @GetMapping("{id}")
    fun getOrder(@PathVariable(value="id") orderId : String): ResponseEntity<Order> {
        return ResponseEntity.ok(orderService.findOrderById(orderId))
    }
}
