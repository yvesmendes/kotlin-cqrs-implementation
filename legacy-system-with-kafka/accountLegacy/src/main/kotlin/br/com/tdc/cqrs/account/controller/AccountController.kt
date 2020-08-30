package br.com.tdc.cqrs.account.controller

import br.com.tdc.cqrs.account.services.AccountService
import localhost.accounts.*
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.ws.server.endpoint.annotation.Endpoint
import org.springframework.ws.server.endpoint.annotation.PayloadRoot
import org.springframework.ws.server.endpoint.annotation.RequestPayload
import org.springframework.ws.server.endpoint.annotation.ResponsePayload

private const val NAMESPACE_URI = "http://localhost/accounts/"

@Endpoint
class AccountController(val accountService: AccountService) {

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateBalanceRequest")
    @ResponsePayload
    fun updateBalance(@RequestPayload updateBalanceRequest: UpdateBalanceRequest): UpdateBalanceResponse {
        return accountService.updateBalance(updateBalanceRequest)
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAccountRequest")
    @ResponsePayload
    fun getById(@RequestPayload getAccountRequest: GetAccountRequest): GetAccountResponse {
        return accountService.getById(getAccountRequest)
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCustomerAccountRequest")
    @ResponsePayload
    fun getByCustomerId(@RequestPayload getCustomerAccountRequest: GetCustomerAccountRequest): GetCustomerAccountResponse {
        return accountService.getByCustomerId(getCustomerAccountRequest)
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteAccountRequest")
    @ResponsePayload
    fun deleteAccount(@RequestPayload deleteAccountRequest: DeleteAccountRequest) {
        return accountService.deleteById(deleteAccountRequest)
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createAccountRequest")
    @ResponsePayload
    fun createAccount(@RequestPayload createAccountRequest: CreateAccountRequest): CreateAccountResponse {
        return accountService.createAccount(createAccountRequest)
    }
}
