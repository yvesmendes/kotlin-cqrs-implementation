package br.com.tdc.cqrs.account.dto

import br.com.tdc.cqrs.account.enums.AccountStatus
import java.math.BigDecimal


data class AccountDTO(val customerId: Long, val customerName : String, val accountNumber : String, var balance: BigDecimal, var accountStatus : AccountStatus)
