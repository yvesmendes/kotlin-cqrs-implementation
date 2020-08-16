package br.com.tdc.cqrs.account.listener

import br.com.tdc.cqrs.account.domain.Account
import br.com.tdc.cqrs.account.enums.AccountStatus
import br.com.tdc.cqrs.account.repository.AccountRepository
import br.com.tdc.cqrs.customer.dto.CustomerMessageDTO
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.Message
import org.springframework.amqp.core.MessageListener
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class AddQueueListener(private val messageConverter: MessageConverter, private val accountRepository: AccountRepository) : MessageListener {
    private val log = LoggerFactory.getLogger(javaClass)


    override fun onMessage(message: Message?) {
        log.info("receive message from ${message?.messageProperties?.consumerQueue}")
        message?.let {
            val customerMessageDTO: CustomerMessageDTO = messageConverter.fromMessage(it) as CustomerMessageDTO
            log.info("customer: $customerMessageDTO")

            val account = Account(customerId = customerMessageDTO.id, accountNumber = customerMessageDTO.accountNumber, balance = BigDecimal.ZERO, accountStatus = AccountStatus.OPEN)
            accountRepository.save(account)

        } ?: log.warn("No message found")

    }
}