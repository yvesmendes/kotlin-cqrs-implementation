package br.com.tdc.cqrs.customer.configuration

object QueueDefinition {

    const val CUSTOMER_QUEUE_ADD = "CUSTOMER-QUEUE-ADD"
    const val CUSTOMER_QUEUE_REMOVE = "CUSTOMER-QUEUE-REMOVE"
    const val CUSTOMER_EXCHANGE = "customerExchange"
    const val CUSTOMER_QUEUE_ADD_KEY = "add"
    const val CUSTOMER_QUEUE_REMOVE_KEY = "delete"
    const val DLQ_EXCHANGE = "DLQ-CUSTOMER-EXCHANGE"
    const val DLQ_QUEUE = "DLQ-CUSTOMER-QUEUE"
    const val DLQ_BINDING_KEY = "TO-DLQ"

    const val CONSUMER_ACCOUNT_QUEUE = "ACCOUNT-QUEUE"
    const val CONSUMER_ACCOUNT_QUEUE_DEBIT = "ACCOUNT-QUEUE-DEBIT"
}