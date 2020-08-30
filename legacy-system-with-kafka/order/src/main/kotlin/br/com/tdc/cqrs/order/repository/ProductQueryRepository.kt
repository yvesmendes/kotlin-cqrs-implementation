package br.com.tdc.cqrs.order.repository

import br.com.tdc.cqrs.order.domain.ProductQuery
import org.springframework.data.repository.CrudRepository

interface ProductQueryRepository : CrudRepository<ProductQuery, String>
