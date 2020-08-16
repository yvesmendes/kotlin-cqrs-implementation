package br.com.tdc.cqrs.account.listener

import br.com.tdc.cqrs.account.configuration.QueueAccountDefinition
import br.com.tdc.cqrs.account.dto.AccountMessageDTO
import br.com.tdc.cqrs.account.enums.BalanceRequestType
import br.com.tdc.cqrs.account.repository.AccountRepository
import br.com.tdc.cqrs.order.domain.Order
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.Message
import org.springframework.amqp.core.MessageListener
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.stereotype.Service

@Service
class CatalogQueueListener(private val messageConverter: MessageConverter,
                           private val accountRepository: AccountRepository,
                           private val rabbitTemplate: RabbitTemplate) : MessageListener {
    private val log = LoggerFactory.getLogger(javaClass)

    override fun onMessage(message: Message?) {

        log.info("receive message from ${message?.messageProperties?.consumerQueue}")
        message?.let {
            var order: Order = messageConverter.fromMessage(it) as Order
            log.info("order: $order")
            val account = accountRepository.findByCustomerId(order.customerId)
            var routingKey = QueueAccountDefinition.ACCOUNT_QUEUE_PLACED_KEY
            if(account.balance >= order.total){
                account.balance -= order.total
                accountRepository.save(account)
                val accountMessageDTO = AccountMessageDTO(accountNumber = account.accountNumber, balance = account.balance, customerId = account.customerId)
                rabbitTemplate.convertAndSend(QueueAccountDefinition.ACCOUNT_EXCHANGE, BalanceRequestType.DEBIT.routingKey, accountMessageDTO)
            }else{
                routingKey = QueueAccountDefinition.ACCOUNT_QUEUE_INVALID_KEY
            }

            rabbitTemplate.convertAndSend(QueueAccountDefinition.ACCOUNT_EXCHANGE, routingKey, order)

        } ?: log.warn("No message found")
    }
}