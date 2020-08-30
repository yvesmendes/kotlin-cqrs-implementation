package br.com.tdc.cqrs.account.sink

import br.com.tdc.cqrs.account.configuration.QueueAccountDefinition
import br.com.tdc.cqrs.account.domain.Account
import br.com.tdc.cqrs.account.dto.AccountMessageDTO
import br.com.tdc.cqrs.account.dto.Envelope
import br.com.tdc.cqrs.account.enums.AccountStatus
import br.com.tdc.cqrs.account.repository.AccountRepository
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.cloud.stream.messaging.Sink
import org.springframework.stereotype.Service


@Service
class ReadModelUpdater(private val accountRepository: AccountRepository, private val rabbitTemplate: RabbitTemplate){

    @StreamListener(Sink.INPUT)
    fun handle(message: Envelope) {
        when{
            message.isUpdate() -> updateAccount(message)
            message.isInsert() -> persistAccount(message)
            message.isDelete() -> deleteAccount(message)
        }
    }

    private fun updateAccount(message: Envelope) {
        val accountModel = message.payload.after
        val accountModelBefore = message.payload.before
        var account = accountRepository.findById(accountModel.account_number).get()
        account.balance = accountModel.balance.toBigDecimal()

        if(accountModel.balance != accountModelBefore.balance){
            val accountMessageDTO = AccountMessageDTO(accountNumber = account.accountNumber,
                    balance = account.balance, customerId = account.customerId)

            val operation = if(accountModelBefore.balance > accountModel.balance){
                "debit"
            }else{
                "credit"
            }

            rabbitTemplate.convertAndSend(QueueAccountDefinition.ACCOUNT_EXCHANGE, operation, accountMessageDTO)
        }

        when(accountModel.account_status){
            "0" -> account.accountStatus = AccountStatus.OPEN
            "1" -> account.accountStatus = AccountStatus.CLOSED
        }

        accountRepository.save(account)
    }

    private fun deleteAccount(message: Envelope) {
        val account = accountRepository.findById(message.payload.before.account_number).get()
        accountRepository.delete(account)
    }

    private fun persistAccount(message: Envelope) {
        val accountModel = message.payload.after
        val account = Account(accountNumber = accountModel.account_number,
                balance = accountModel.balance.toBigDecimal(),
                customerId = accountModel.customer_id,
                accountStatus = AccountStatus.OPEN )
        accountRepository.save(account)
    }
}

