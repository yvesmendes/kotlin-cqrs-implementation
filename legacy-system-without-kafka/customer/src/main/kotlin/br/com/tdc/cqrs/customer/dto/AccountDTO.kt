package br.com.tdc.cqrs.customer.dto

import java.math.BigDecimal


data class AccountDTO(val accountNumber : String, val balance: BigDecimal)
