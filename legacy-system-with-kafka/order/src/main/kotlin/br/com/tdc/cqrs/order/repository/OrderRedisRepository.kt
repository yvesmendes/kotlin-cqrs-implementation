package br.com.tdc.cqrs.order.repository

import br.com.tdc.cqrs.order.domain.OrderRedis
import org.springframework.data.repository.CrudRepository

interface OrderRedisRepository : CrudRepository<OrderRedis, Long>
