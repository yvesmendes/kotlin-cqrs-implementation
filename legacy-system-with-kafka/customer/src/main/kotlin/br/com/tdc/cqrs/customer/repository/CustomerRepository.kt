package br.com.tdc.cqrs.customer.repository

import br.com.tdc.cqrs.customer.domain.Customer
import org.springframework.data.repository.CrudRepository

interface CustomerRepository : CrudRepository<Customer, Long>
