package br.com.tdc.cqrs.account.services

import br.com.tdc.cqrs.account.domain.Account
import br.com.tdc.cqrs.account.dto.AccountDTO
import br.com.tdc.cqrs.account.dto.BalanceRequest

interface AccountService {
    fun saveAccount(accountDTO: AccountDTO) : Account
    fun deleteById(id: String)
    fun getById(id: String): Account
    fun updateBalance(balanceRequest: BalanceRequest)
}