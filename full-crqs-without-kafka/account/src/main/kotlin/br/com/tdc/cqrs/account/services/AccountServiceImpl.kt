package br.com.tdc.cqrs.account.services

import br.com.tdc.cqrs.account.configuration.QueueAccountDefinition
import br.com.tdc.cqrs.account.domain.Account
import br.com.tdc.cqrs.account.dto.AccountDTO
import br.com.tdc.cqrs.account.dto.AccountMessageDTO
import br.com.tdc.cqrs.account.dto.BalanceRequest
import br.com.tdc.cqrs.account.enums.AccountStatus
import br.com.tdc.cqrs.account.exceptions.NotFoundException
import br.com.tdc.cqrs.account.repository.AccountRepository
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.*

@Service
class AccountServiceImpl(private val accountRepository: AccountRepository, private val rabbitTemplate : RabbitTemplate) : AccountService {
    override fun updateBalance(balanceRequest: BalanceRequest) {
        val account = accountRepository.findById(balanceRequest.accountNumber).orElseThrow { NotFoundException() }

        val calculatedAmount = BigDecimal(balanceRequest.balanceRequestType.multiplier).multiply(balanceRequest.amount)
        account.balance = account.balance.add(calculatedAmount)
        accountRepository.save(account)

        val accountMessageDTO = AccountMessageDTO(accountNumber = account.accountNumber, balance = account.balance, customerId = account.customerId)
        rabbitTemplate.convertAndSend(QueueAccountDefinition.ACCOUNT_EXCHANGE, balanceRequest.balanceRequestType.routingKey, accountMessageDTO)
    }

    override fun getById(id: String): Account {
        return accountRepository.findById(id).orElseThrow { NotFoundException() }
    }

    override fun deleteById(id: String) {
        val account = accountRepository.findById(id).orElseThrow { NotFoundException() }
        account.accountStatus = AccountStatus.CLOSED
        accountRepository.save(account)
    }

    override fun saveAccount(accountDTO: AccountDTO): Account {
        val account = Account(accountNumber = UUID.randomUUID().toString(), customerId = accountDTO.customerId, balance = BigDecimal.ZERO)
        return accountRepository.save(account)
    }
}