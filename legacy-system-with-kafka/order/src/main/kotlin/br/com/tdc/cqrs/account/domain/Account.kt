package br.com.tdc.cqrs.account.domain

import br.com.tdc.cqrs.account.enums.AccountStatus
import java.math.BigDecimal


data class Account(val accountNumber : String = "", val customerId: Long, var balance: BigDecimal, var accountStatus: AccountStatus = AccountStatus.OPEN){
    constructor() : this(accountNumber = "", customerId = -1, balance = BigDecimal.ZERO)
}
