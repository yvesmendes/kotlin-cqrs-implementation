package br.com.tdc.cqrs.account.controller

import br.com.tdc.cqrs.account.domain.Account
import br.com.tdc.cqrs.account.dto.BalanceRequest
import br.com.tdc.cqrs.account.services.AccountService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("v1/accounts")
class AccountController(val accountService: AccountService) {


    @PutMapping("/balance")
    fun debit(@RequestBody balanceRequest: BalanceRequest): ResponseEntity<Unit> {
        accountService.updateBalance(balanceRequest)
        return ResponseEntity.ok().build()
    }

    @GetMapping("{id}")
    fun get(@PathVariable id: String): ResponseEntity<Account> {
        return ResponseEntity.ok(accountService.getById(id))
    }
}
