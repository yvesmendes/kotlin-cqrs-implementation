package br.com.tdc.cqrs.customer.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Customer(@Id @GeneratedValue val id : Long = -1, val name: String, var email: String, var password: String){
    constructor() : this(name = "", email = "", password = "")
}
