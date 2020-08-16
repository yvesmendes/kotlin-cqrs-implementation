package br.com.tdc.cqrs.account.repository

import br.com.tdc.cqrs.account.domain.Account
import org.springframework.data.repository.CrudRepository

interface AccountRepository : CrudRepository<Account, String>{

    fun findByCustomerId(id : Long) : Account
}
