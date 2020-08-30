package br.com.tdc.cqrs.account.listener

import br.com.tdc.cqrs.account.configuration.QueueAccountDefinition
import br.com.tdc.cqrs.account.domain.Account
import br.com.tdc.cqrs.account.dto.AccountMessageDTO
import br.com.tdc.cqrs.account.enums.BalanceRequestType
import br.com.tdc.cqrs.account.repository.AccountRepository
import br.com.tdc.cqrs.order.domain.Order
import localhost.accounts.AccountPort
import localhost.accounts.AccountPortService
import localhost.accounts.BalanceRequest
import localhost.accounts.UpdateBalanceRequest
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

            val service = AccountPortService()
            val accountPort = service.accountPortSoap11

            if(account.balance >= order.total){
                accountPort.updateBalance(createRequest(account, order))
                val accountMessageDTO = AccountMessageDTO(accountNumber = account.accountNumber, balance = account.balance, customerId = account.customerId)
                rabbitTemplate.convertAndSend(QueueAccountDefinition.ACCOUNT_EXCHANGE, BalanceRequestType.DEBIT.routingKey, accountMessageDTO)
            }else{
                routingKey = QueueAccountDefinition.ACCOUNT_QUEUE_INVALID_KEY
            }

            rabbitTemplate.convertAndSend(QueueAccountDefinition.ACCOUNT_EXCHANGE, routingKey, order)

        } ?: log.warn("No message found")
    }

    private fun createRequest(account: Account, order: Order) : UpdateBalanceRequest {
        var request = UpdateBalanceRequest()
        var balanceRequest = BalanceRequest()
        balanceRequest.accountNumber = account.accountNumber
        balanceRequest.amount = order.total.toDouble()
        balanceRequest.customerId = account.customerId
        balanceRequest.balanceRequestType = localhost.accounts.BalanceRequestType.DEBIT
        request.requestAccount = balanceRequest

        return request
    }
}