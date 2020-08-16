package br.com.tdc.cqrs.order.domain

import java.math.BigDecimal
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class ProductQuery(@Id val id : String = "", var name: String = "", var price  : BigDecimal = BigDecimal.ZERO)
