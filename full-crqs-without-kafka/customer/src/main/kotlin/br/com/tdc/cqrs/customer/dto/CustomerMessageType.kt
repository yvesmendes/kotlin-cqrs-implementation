package br.com.tdc.cqrs.customer.dto

enum class CustomerMessageType(val operation: String) {
    ADD("A"), REMOVE("R");
}