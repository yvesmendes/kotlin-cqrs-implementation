package br.com.tdc.cqrs.account.services

import br.com.tdc.cqrs.account.domain.Account
import br.com.tdc.cqrs.account.exceptions.NotFoundException
import br.com.tdc.cqrs.account.repository.AccountRepository
import org.springframework.stereotype.Service

@Service
class AccountServiceImpl(private val accountRepository: AccountRepository) : AccountService {

    override fun getById(id: String): Account {
        return accountRepository.findById(id).orElseThrow { NotFoundException() }
    }

    override fun getByCustomerId(id: Long): Account {
        return accountRepository.findByCustomerId(id)
    }
}