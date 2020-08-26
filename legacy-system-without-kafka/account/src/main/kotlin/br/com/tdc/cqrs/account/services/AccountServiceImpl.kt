package br.com.tdc.cqrs.account.services

import br.com.tdc.cqrs.account.domain.Account
import br.com.tdc.cqrs.account.dto.AccountDTO
import br.com.tdc.cqrs.account.dto.BalanceRequestDTO
import br.com.tdc.cqrs.account.enums.AccountStatus
import localhost.accounts.AccountPortService
import localhost.accounts.GetAccountRequest
import localhost.accounts.GetCustomerAccountRequest
import localhost.accounts.UpdateBalanceRequest
import localhost.accounts.BalanceRequest
import localhost.accounts.CreateAccountRequest
import localhost.accounts.DeleteAccountRequest
import org.springframework.stereotype.Service

@Service
class AccountServiceImpl() : AccountService {

    override fun getById(id: String): Account {
        var getById = GetAccountRequest()
        getById.id = id

        val accountOrigin = AccountPortService().accountPortSoap11.getAccount(getById)

        return createAccountResponse(accountOrigin.account)
    }

    override fun getByCustomerId(id: Long): Account {
        var getByCustomerId = GetCustomerAccountRequest()
        getByCustomerId.customerId = id
        val accountOrigin = AccountPortService().accountPortSoap11.getCustomerAccount(getByCustomerId)

        return createAccountResponse(accountOrigin.account)
    }

    override fun updateBalance(account : BalanceRequestDTO) : Account {
        var accountRequest = UpdateBalanceRequest()

        var balanceRequest = BalanceRequest()

        balanceRequest.amount = account.amount.toDouble()
        balanceRequest.accountNumber = account.accountNumber

        var balanceRequestType = localhost.accounts.BalanceRequestType.CREDIT

        if(account.balanceRequestType.routingKey != localhost.accounts.BalanceRequestType.CREDIT.value()){
            balanceRequestType = localhost.accounts.BalanceRequestType.DEBIT
        }
        balanceRequest.balanceRequestType = balanceRequestType

        accountRequest.requestAccount = balanceRequest

        val accountUpdateBalanceResponse = AccountPortService().accountPortSoap11.updateBalance(accountRequest)

        return createAccountResponse(accountUpdateBalanceResponse.account)
    }

    override fun createAccount(account: AccountDTO): Account {
        var accountRequest = CreateAccountRequest()
        accountRequest.customerId = account.customerId

        return createAccountResponse(AccountPortService().accountPortSoap11.createAccount(accountRequest).customerId)
    }

    override fun deleteById(id: String) {
        var getById = DeleteAccountRequest()
        getById.id = id
        AccountPortService().accountPortSoap11.deleteAccount(getById)
    }

    private fun createAccountResponse(accountOrigin: localhost.accounts.Account): Account {
        var accountStatus = AccountStatus.OPEN
        if (accountOrigin.accountStatus.value() != localhost.accounts.AccountStatus.OPEN.value()) {
            accountStatus = AccountStatus.CLOSED
        }
        return Account(accountNumber = accountOrigin.accountNumber, balance = accountOrigin.balance.toBigDecimal()
                , customerId = accountOrigin.customerId, accountStatus = accountStatus)
    }
}