package br.com.tdc.cqrs.order.configuration

object QueueOrderDefinition {
    const val CONSUMER_CATALOG_QUEUE = "CATALOG-QUEUE"
    const val CONSUMER_CATALOG_QUEUE_PLACED = "CATALOG-QUEUE-PLACED"
    const val ORDER_EXCHANGE = "orderExchange"
    const val ORDER_QUEUE_KEY = "update"
    const val ORDER_QUEUE_INVALID_KEY = "invalid"

}