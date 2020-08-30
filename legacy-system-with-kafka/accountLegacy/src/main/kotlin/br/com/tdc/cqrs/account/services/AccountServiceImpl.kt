package br.com.tdc.cqrs.account.services

import br.com.tdc.cqrs.account.domain.Account
import br.com.tdc.cqrs.account.dto.AccountDTO
import br.com.tdc.cqrs.account.dto.AccountMessageDTO
import br.com.tdc.cqrs.account.enums.AccountStatus
import br.com.tdc.cqrs.account.exceptions.InsuficientBalanceException
import br.com.tdc.cqrs.account.exceptions.NotFoundException
import br.com.tdc.cqrs.account.repository.AccountRepository
import localhost.accounts.*
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.*

@Service
class AccountServiceImpl(private val accountRepository: AccountRepository) : AccountService {
    override fun updateBalance(updateBalanceRequest: UpdateBalanceRequest): UpdateBalanceResponse {
        val account = accountRepository.findById(updateBalanceRequest.requestAccount.accountNumber).orElseThrow { NotFoundException() }

        var multiplier = 1

        if( updateBalanceRequest.requestAccount.balanceRequestType.value() == "debit"){
            multiplier = -1
            if(updateBalanceRequest.requestAccount.amount > account.balance.toDouble()){
                throw InsuficientBalanceException()
            }
        }

        val calculatedAmount = BigDecimal(multiplier).multiply(updateBalanceRequest.requestAccount.amount.toBigDecimal())
        account.balance = account.balance.add(calculatedAmount)
        accountRepository.save(account)

        val updateBalanceResponse = UpdateBalanceResponse()
        updateBalanceResponse.account = createAccountWS(account)
        return updateBalanceResponse
    }

    override fun createAccount(createAccountRequest: CreateAccountRequest): CreateAccountResponse {
        val account = Account(accountNumber = createAccountRequest.accountId,
                customerId = createAccountRequest.customerId,
                balance = BigDecimal.ZERO)

        accountRepository.save(account)

        var createAccountResponse = CreateAccountResponse()

        createAccountResponse.customerId = createAccountWS(account)
        return createAccountResponse
    }

    private fun createAccountWS(account: Account): localhost.accounts.Account {
        var accountResponse = localhost.accounts.Account()
        accountResponse.accountNumber = account.accountNumber
        accountResponse.balance = account.balance.toDouble()
        accountResponse.customerId = account.customerId

        if (account.accountStatus == AccountStatus.OPEN) {
            accountResponse.accountStatus = localhost.accounts.AccountStatus.OPEN
        } else {
            accountResponse.accountStatus = localhost.accounts.AccountStatus.CLOSED
        }
        return accountResponse
    }

    override fun getById(accountRequest: GetAccountRequest): GetAccountResponse {
        val accountPersisted = accountRepository.findById(accountRequest.id).orElseThrow { NotFoundException() }
        var account = createAccountWS(accountPersisted)
        var accountResponse = GetAccountResponse()
        accountResponse.account = account

        return accountResponse
    }

    override fun getByCustomerId(customerAccountRequest: GetCustomerAccountRequest): GetCustomerAccountResponse {
        var account = createAccountWS(accountRepository.findByCustomerId(customerAccountRequest.customerId))
        var customerAccountResponse = GetCustomerAccountResponse()
        customerAccountResponse.account = account
        return customerAccountResponse
    }

    override fun deleteById(deleteAccountRequest: DeleteAccountRequest) {
        val account = accountRepository.findById(deleteAccountRequest.id).orElseThrow { NotFoundException() }
        account.accountStatus = AccountStatus.CLOSED
        accountRepository.save(account)
    }
}