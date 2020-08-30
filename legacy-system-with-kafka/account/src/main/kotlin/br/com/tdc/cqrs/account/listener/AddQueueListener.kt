package br.com.tdc.cqrs.account.listener

import br.com.tdc.cqrs.account.repository.AccountRepository
import br.com.tdc.cqrs.customer.dto.CustomerMessageDTO
import localhost.accounts.AccountPortService
import localhost.accounts.CreateAccountRequest
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.Message
import org.springframework.amqp.core.MessageListener
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.stereotype.Service

@Service
class AddQueueListener(private val messageConverter: MessageConverter) : MessageListener {
    private val log = LoggerFactory.getLogger(javaClass)


    override fun onMessage(message: Message?) {
        log.info("receive message from ${message?.messageProperties?.consumerQueue}")
        message?.let {
            val customerMessageDTO: CustomerMessageDTO = messageConverter.fromMessage(it) as CustomerMessageDTO
            log.info("customer: $customerMessageDTO")

            val service = AccountPortService()
            val accountPort = service.accountPortSoap11
            var request = CreateAccountRequest()
            request.accountId = customerMessageDTO.accountNumber
            request.customerId = customerMessageDTO.id

            accountPort.createAccount(request)
        } ?: log.warn("No message found")

    }
}