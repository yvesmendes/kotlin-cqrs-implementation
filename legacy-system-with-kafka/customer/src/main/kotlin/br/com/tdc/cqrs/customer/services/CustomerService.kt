package br.com.tdc.cqrs.customer.services

import br.com.tdc.cqrs.customer.domain.Customer
import br.com.tdc.cqrs.customer.dto.CustomerDTO
import br.com.tdc.cqrs.customer.dto.CustomerResponseDTO

interface CustomerService {
    fun saveCustomer(customerDTO : CustomerDTO) : CustomerResponseDTO
    fun deleteById(id: Long)
    fun getById(id: Long): CustomerResponseDTO
    fun updateCustomer(customer: Customer)
}