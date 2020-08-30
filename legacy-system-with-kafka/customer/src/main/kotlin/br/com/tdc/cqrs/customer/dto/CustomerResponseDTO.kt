package br.com.tdc.cqrs.customer.dto


data class CustomerResponseDTO(val id : Long, val name: String, val mail: String, val account : AccountDTO)
