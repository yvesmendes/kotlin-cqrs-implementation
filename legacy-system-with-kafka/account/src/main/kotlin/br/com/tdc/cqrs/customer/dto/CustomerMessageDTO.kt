package br.com.tdc.cqrs.customer.dto


data class CustomerMessageDTO(val id: Long, val name: String, val operation : String, val accountNumber : String)
