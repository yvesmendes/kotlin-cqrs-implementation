package br.com.tdc.cqrs.account.dto

import br.com.tdc.cqrs.account.enums.BalanceRequestType
import java.math.BigDecimal


data class BalanceRequest(val amount: BigDecimal, val accountNumber : String, val balanceRequestType: BalanceRequestType)
