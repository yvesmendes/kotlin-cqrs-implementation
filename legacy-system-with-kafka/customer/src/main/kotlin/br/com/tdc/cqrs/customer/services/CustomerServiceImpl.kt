package br.com.tdc.cqrs.customer.services

import br.com.tdc.cqrs.customer.configuration.QueueDefinition
import br.com.tdc.cqrs.customer.domain.Account
import br.com.tdc.cqrs.customer.domain.Customer
import br.com.tdc.cqrs.customer.dto.*
import br.com.tdc.cqrs.customer.exceptions.NotFoundException
import br.com.tdc.cqrs.customer.repository.AccountRepository
import br.com.tdc.cqrs.customer.repository.CustomerRepository
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.*

@Service
class CustomerServiceImpl(private val customerRepository : CustomerRepository, private val accountRepository: AccountRepository, private val rabbitTemplate: RabbitTemplate) : CustomerService {
    override fun updateCustomer(customer: Customer) {
        val customerPersisted = customerRepository.findById(customer.id).orElseThrow { NotFoundException() }
        customerPersisted.email = customer.email
        customerPersisted.password = customer.password
        customerRepository.save(customerPersisted)
    }

    override fun getById(id: Long): CustomerResponseDTO {
        val customer = customerRepository.findById(id).orElseThrow { NotFoundException() }
        val account = accountRepository.findByCustomerId(customerId = customer.id)
        val accountDTO = AccountDTO(accountNumber = account.accountNumber, balance = account.balance)
        return CustomerResponseDTO(id=customer.id, mail = customer.email, name = customer.name, account = accountDTO)
    }

    override fun deleteById(id: Long) {
        val customer = customerRepository.findById(id).orElseThrow { NotFoundException() }
        customerRepository.delete(customer)
        val customerMessageDTO = CustomerMessageDTO(id=customer.id, name = customer.name,
                operation = CustomerMessageType.REMOVE.operation,accountNumber =  "")
        rabbitTemplate.convertAndSend(QueueDefinition.CUSTOMER_EXCHANGE, QueueDefinition.CUSTOMER_QUEUE_REMOVE_KEY, customerMessageDTO)
    }

    override fun saveCustomer(customerDTO: CustomerDTO): CustomerResponseDTO {
        val customer = Customer(name = customerDTO.name, email = customerDTO.mail, password = customerDTO.password)
        val persistedCustomer = customerRepository.save(customer)

        val account = AccountDTO(accountNumber = UUID.randomUUID().toString(), balance = BigDecimal.ZERO)
        handleAccountCreation(persistedCustomer, account)

        return CustomerResponseDTO(id=persistedCustomer.id, name = persistedCustomer.name,
                mail = persistedCustomer.email, account = account)
    }

    private fun handleAccountCreation(persistedCustomer: Customer, accountDTO: AccountDTO) {
        val customerMessageDTO = CustomerMessageDTO(id=persistedCustomer.id, name = persistedCustomer.name,
                operation = CustomerMessageType.ADD.operation, accountNumber = accountDTO.accountNumber)

        accountRepository.save(Account(accountNumber = accountDTO.accountNumber, balance = accountDTO.balance, customerId = persistedCustomer.id))
        rabbitTemplate.convertAndSend(QueueDefinition.CUSTOMER_EXCHANGE, QueueDefinition.CUSTOMER_QUEUE_ADD_KEY, customerMessageDTO)
    }
}