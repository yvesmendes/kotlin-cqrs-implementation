package br.com.tdc.cqrs.account.controller

import br.com.tdc.cqrs.account.domain.Account
import br.com.tdc.cqrs.account.dto.AccountDTO
import br.com.tdc.cqrs.account.dto.BalanceRequestDTO
import br.com.tdc.cqrs.account.services.AccountService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("v1/accounts")
class AccountController(val accountService: AccountService) {


    @PutMapping("/balance")
    fun debit(@RequestBody balanceRequest: BalanceRequestDTO): ResponseEntity<Account> {
        return ResponseEntity.ok(accountService.updateBalance(balanceRequest))
    }

    @GetMapping("{id}")
    fun get(@PathVariable id: String): ResponseEntity<Account> {
        return ResponseEntity.ok(accountService.getById(id))
    }

    @GetMapping("/customers/{id}")
    fun getByCustomer(@PathVariable id: Long): ResponseEntity<Account> {
        return ResponseEntity.ok(accountService.getByCustomerId(id))
    }
    @PostMapping
    fun createAccount(@RequestBody account: AccountDTO): ResponseEntity<Account> {
        return ResponseEntity.ok(accountService.createAccount(account))
    }
}
