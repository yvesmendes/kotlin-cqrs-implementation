package br.com.tdc.cqrs.account.dto

import br.com.tdc.cqrs.account.domain.Account

data class AccountStateChanged(val op : String, val ts_ms : String, val before : AccountModel, val after : AccountModel) {

    constructor() : this(op = "", ts_ms = "", before = AccountModel(), after = AccountModel())
}