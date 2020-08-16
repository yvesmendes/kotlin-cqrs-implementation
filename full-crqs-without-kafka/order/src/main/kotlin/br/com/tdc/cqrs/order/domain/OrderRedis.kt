package br.com.tdc.cqrs.order.domain

import org.springframework.data.redis.core.RedisHash
import java.math.BigDecimal

@RedisHash("Order")
data class OrderRedis(val id: Long = -1, val orderId : String = "", var products: MutableList<ProductRedis> = mutableListOf<ProductRedis>(), var total : BigDecimal = BigDecimal.ZERO)
