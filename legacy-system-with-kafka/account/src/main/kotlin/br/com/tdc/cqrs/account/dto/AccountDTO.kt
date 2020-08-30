package br.com.tdc.cqrs.account.dto


data class AccountDTO(val customerId: Long = -1) {
    constructor() : this(customerId = -1)
}

