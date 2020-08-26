package br.com.tdc.cqrs.customer.dto

import java.math.BigDecimal

data class AccountRequestDTO(val customerId: Long, var balance: BigDecimal){
    constructor() : this(customerId = -1, balance = BigDecimal.ZERO)
}
