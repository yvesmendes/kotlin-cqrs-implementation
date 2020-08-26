package br.com.tdc.cqrs.customer.controller

import br.com.tdc.cqrs.customer.domain.Customer
import br.com.tdc.cqrs.customer.dto.CustomerDTO
import br.com.tdc.cqrs.customer.dto.CustomerResponseDTO
import br.com.tdc.cqrs.customer.services.CustomerService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("v1/customers")
class CustomerController(private val customerService: CustomerService) {

    private val log = LoggerFactory.getLogger(javaClass)

    @PostMapping
    fun save(@RequestBody customer: CustomerDTO): ResponseEntity<CustomerResponseDTO> {
        log.info("Add customer: $customer")
        return ResponseEntity.ok(customerService.saveCustomer(customer))
    }

    @PutMapping
    fun alter(@RequestBody customer: Customer): ResponseEntity<Unit> {
        log.info("Alter customer: $customer")
        customerService.updateCustomer(customer)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Unit> {
        log.info("Delete customer with id: $id")
        val customer = customerService.getById(id)
        customerService.deleteById(customer.id)
        return ResponseEntity.ok().build()
    }

    @GetMapping("{id}")
    fun get(@PathVariable id: Long): ResponseEntity<CustomerResponseDTO> {
        log.info("Retrieve customer with id: $id")
        return ResponseEntity.ok(customerService.getById(id))
    }
}
