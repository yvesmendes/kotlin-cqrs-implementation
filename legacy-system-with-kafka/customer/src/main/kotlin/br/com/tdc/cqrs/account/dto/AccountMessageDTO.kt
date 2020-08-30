package br.com.tdc.cqrs.account.dto

import java.math.BigDecimal


data class AccountMessageDTO(val accountNumber: String, val balance: BigDecimal, val customerId : Long)
