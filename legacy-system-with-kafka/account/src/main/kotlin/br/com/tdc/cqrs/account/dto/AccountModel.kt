package br.com.tdc.cqrs.account.dto


data class AccountModel(val account_number : String = "", val customer_id: Long, var balance: Double, var account_status: String = ""){
    constructor() : this(account_number = "", customer_id = -1, balance = Double.MIN_VALUE)
}
