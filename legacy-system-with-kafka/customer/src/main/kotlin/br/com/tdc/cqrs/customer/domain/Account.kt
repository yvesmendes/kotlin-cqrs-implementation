package br.com.tdc.cqrs.customer.domain

import java.math.BigDecimal
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Account(@Id val accountNumber : String = "", var balance: BigDecimal = BigDecimal.ZERO, val customerId: Long = -1)

