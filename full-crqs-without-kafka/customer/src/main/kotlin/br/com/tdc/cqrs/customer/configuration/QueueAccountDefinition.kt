package br.com.tdc.cqrs.customer.configuration

object QueueAccountDefinition {
    const val ACCOUNT_QUEUE_ADD = "ACCOUNT-QUEUE"
    const val ACCOUNT_QUEUE_DEBIT = "ACCOUNT-QUEUE-DEBIT"
    const val ACCOUNT_QUEUE_PLACED = "ACCOUNT-QUEUE-PLACED"
    const val ACCOUNT_QUEUE_INVALID = "ACCOUNT-QUEUE-INVALID"
    const val ACCOUNT_EXCHANGE = "accountExchange"
    const val ACCOUNT_QUEUE_CREDIT_KEY = "credit"
    const val ACCOUNT_QUEUE_DEBIT_KEY = "debit"
    const val ACCOUNT_QUEUE_PLACED_KEY = "placed"
    const val ACCOUNT_QUEUE_INVALID_KEY = "unplaced"
    const val DLQ_EXCHANGE = "DLQ-ACCOUNT-EXCHANGE"
    const val DLQ_QUEUE = "DLQ-ACCOUNT-QUEUE"
    const val DLQ_BINDING_KEY = "TO-DLQ"
}