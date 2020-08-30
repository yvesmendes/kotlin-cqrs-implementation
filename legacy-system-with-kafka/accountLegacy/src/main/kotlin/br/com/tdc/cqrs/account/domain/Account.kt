package br.com.tdc.cqrs.account.domain

import br.com.tdc.cqrs.account.enums.AccountStatus
import java.math.BigDecimal
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Account(@Id val accountNumber : String = "", val customerId: Long, var balance: BigDecimal, var accountStatus: AccountStatus = AccountStatus.OPEN){
    constructor() : this(accountNumber = "", customerId = -1, balance = BigDecimal.ZERO)
}
