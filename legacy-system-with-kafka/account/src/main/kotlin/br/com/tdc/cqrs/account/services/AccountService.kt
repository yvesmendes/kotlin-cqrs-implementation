package br.com.tdc.cqrs.account.services

import br.com.tdc.cqrs.account.domain.Account
import br.com.tdc.cqrs.account.dto.AccountDTO
import br.com.tdc.cqrs.account.dto.BalanceRequestDTO

interface AccountService {
    fun getById(id: String): Account
    fun getByCustomerId(id: Long): Account
}