package br.com.tdc.cqrs.account.configuration

object QueueAccountDefinition {
    const val ACCOUNT_EXCHANGE = "accountExchange"
    const val ACCOUNT_QUEUE_PLACED_KEY = "placed"
    const val ACCOUNT_QUEUE_INVALID_KEY = "unplaced"
    const val CUSTOMER_QUEUE_ADD = "CUSTOMER-QUEUE-ADD"
    const val CUSTOMER_QUEUE_REMOVE = "CUSTOMER-QUEUE-REMOVE"
    const val CATALOG_QUEUE_PLACED = "CATALOG-QUEUE-PLACED"
}