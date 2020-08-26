package br.com.tdc.cqrs.order.client

import br.com.tdc.cqrs.account.domain.Account
import br.com.tdc.cqrs.account.dto.BalanceRequestDTO
import br.com.tdc.cqrs.order.dto.CatalogCartDTO
import br.com.tdc.cqrs.order.dto.CatalogCartResponseDTO
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@FeignClient(value = "accountService", url = "\${feign.account.url}")
interface AccountFeignClient {

    @RequestMapping("v1/accounts/balance", method = [RequestMethod.PUT], produces = ["application/json"])
    fun balance(@RequestBody balanceRequest: BalanceRequestDTO): Account

    @RequestMapping("v1/accounts/{id}", method = [RequestMethod.GET], produces = ["application/json"])
    fun get(@PathVariable id: String): Account


    @RequestMapping("v1/accounts/customers/{id}", method = [RequestMethod.GET], produces = ["application/json"])
    fun getByCustomer(@PathVariable id: Long): Account

}