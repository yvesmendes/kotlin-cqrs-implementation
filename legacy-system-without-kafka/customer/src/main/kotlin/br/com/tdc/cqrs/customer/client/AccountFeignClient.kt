package br.com.tdc.cqrs.customer.client

import br.com.tdc.cqrs.customer.dto.Account
import br.com.tdc.cqrs.customer.dto.AccountRequestDTO
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod


@FeignClient(value = "accountService", url = "\${feign.httpService.url}")
interface AccountFeignClient {
    @RequestMapping("v1/accounts/{id}", method = [RequestMethod.GET], produces = ["application/json"])
    fun getById(@PathVariable id: String): Account

    @RequestMapping("v1/accounts/customers/{id}", method = [RequestMethod.GET], produces = ["application/json"])
    fun getByCustomerId(@PathVariable id: Long): Account

    @RequestMapping("v1/accounts/", method = [RequestMethod.POST], produces = ["application/json"])
    fun createAccount(@RequestBody account : AccountRequestDTO): Account

    @RequestMapping("v1/accounts/", method = [RequestMethod.DELETE], produces = ["application/json"])
    fun deleteAccount(@RequestBody account : AccountRequestDTO)
}