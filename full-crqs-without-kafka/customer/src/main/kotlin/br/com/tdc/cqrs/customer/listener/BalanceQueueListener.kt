package br.com.tdc.cqrs.customer.listener

import br.com.tdc.cqrs.account.dto.AccountMessageDTO
import br.com.tdc.cqrs.customer.repository.AccountRepository
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.Message
import org.springframework.amqp.core.MessageListener
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.stereotype.Service

@Service
class BalanceQueueListener(private val messageConverter: MessageConverter, private val accountRepository: AccountRepository) : MessageListener {
    private val log = LoggerFactory.getLogger(javaClass)

    override fun onMessage(message: Message?) {
        log.info("receive message from ${message?.messageProperties?.consumerQueue}")
        message?.let {
            val accountMessageDTO: AccountMessageDTO = messageConverter.fromMessage(it) as AccountMessageDTO
            log.info("customer: $accountMessageDTO")

            var account = accountRepository.findById(accountMessageDTO.accountNumber).get()
            account.balance = accountMessageDTO.balance
            accountRepository.save(account)

        } ?: log.warn("No message found")

    }
}