package br.com.tdc.cqrs.account.enums


enum class BalanceRequestType(val multiplier : Int, val routingKey : String){
    DEBIT(-1, "debit"), CREDIT(1, "credit")
}

