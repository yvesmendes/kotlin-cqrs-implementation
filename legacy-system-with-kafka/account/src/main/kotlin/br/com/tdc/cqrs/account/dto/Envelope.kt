package br.com.tdc.cqrs.account.dto

data class Envelope(val payload: AccountStateChanged) {
    constructor() : this(payload=AccountStateChanged())
    
    fun isUpdate() : Boolean{
        return payload.op == "u"
    }

    fun isInsert(): Boolean {
        return payload.op == "c"
    }

    fun isDelete(): Boolean {
        return payload.op == "d"
    }
}