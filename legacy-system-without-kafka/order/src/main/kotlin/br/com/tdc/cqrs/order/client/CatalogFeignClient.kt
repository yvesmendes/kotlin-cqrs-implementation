package br.com.tdc.cqrs.order.client

import br.com.tdc.cqrs.order.dto.CatalogCartDTO
import br.com.tdc.cqrs.order.dto.CatalogCartResponseDTO
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod


@FeignClient(value = "catalogService", url = "\${feign.httpService.url}")
interface CatalogFeignClient {
    @RequestMapping("v1/catalog/calculate", method = [RequestMethod.POST], produces = ["application/json"])
    fun calculate(@RequestBody catalog: CatalogCartDTO): CatalogCartResponseDTO
}