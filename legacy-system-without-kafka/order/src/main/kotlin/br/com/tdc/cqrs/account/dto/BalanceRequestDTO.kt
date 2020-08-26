package br.com.tdc.cqrs.account.dto

import br.com.tdc.cqrs.account.enums.BalanceRequestType
import java.math.BigDecimal


data class BalanceRequestDTO(val amount: BigDecimal, val accountNumber : String, val balanceRequestType: BalanceRequestType)
