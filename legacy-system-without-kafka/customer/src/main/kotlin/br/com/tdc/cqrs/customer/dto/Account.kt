package br.com.tdc.cqrs.customer.dto

import java.math.BigDecimal
import javax.persistence.Id

data class Account(@Id val accountNumber : String = "", val customerId: Long, var balance: BigDecimal, var accountStatus: AccountStatus = AccountStatus.OPEN){
    constructor() : this(accountNumber = "", customerId = -1, balance = BigDecimal.ZERO)
}
