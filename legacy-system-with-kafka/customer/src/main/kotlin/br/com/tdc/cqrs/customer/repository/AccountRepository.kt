package br.com.tdc.cqrs.customer.repository

import br.com.tdc.cqrs.customer.domain.Account
import org.springframework.data.repository.CrudRepository

interface AccountRepository : CrudRepository<Account, String>{
    fun findByCustomerId(customerId : Long) : Account
}
