package br.com.tdc.cqrs.account.services

import localhost.accounts.*

interface AccountService {
    fun deleteById(deleteAccountRequest: DeleteAccountRequest)
    fun getById(accountRequest: GetAccountRequest): GetAccountResponse
    fun getByCustomerId(customerAccountRequest: GetCustomerAccountRequest): GetCustomerAccountResponse
    fun updateBalance(updateBalanceRequest: UpdateBalanceRequest): UpdateBalanceResponse
    fun createAccount(accountRequest: CreateAccountRequest): CreateAccountResponse
}