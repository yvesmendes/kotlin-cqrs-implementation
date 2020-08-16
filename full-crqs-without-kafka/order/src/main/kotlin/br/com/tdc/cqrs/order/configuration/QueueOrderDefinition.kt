package br.com.tdc.cqrs.order.configuration

object QueueOrderDefinition {
    const val CONSUMER_CATALOG_QUEUE = "CATALOG-QUEUE"
    const val CONSUMER_ACCOUNT_REVERT_QUEUE = "ORDER-QUEUE-INVALID"
    const val CONSUMER_ACCOUNT_QUEUE_PLACED = "ACCOUNT-QUEUE-PLACED"
    const val ORDER_EXCHANGE = "orderExchange"
    const val ORDER_QUEUE_KEY = "update"

}