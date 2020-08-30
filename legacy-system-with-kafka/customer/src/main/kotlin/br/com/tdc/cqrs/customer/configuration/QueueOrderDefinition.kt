package br.com.tdc.cqrs.customer.configuration

object QueueOrderDefinition {
    const val ORDER_QUEUE_ADD = "ORDER-QUEUE"
    const val ORDER_QUEUE_PAID = "ORDER-QUEUE-PAID"
    const val ORDER_EXCHANGE = "orderExchange"
    const val ORDER_QUEUE_KEY = "update"
    const val DLQ_EXCHANGE = "DLQ-ORDER-EXCHANGE"
    const val DLQ_QUEUE = "DLQ-ORDER-QUEUE"
    const val DLQ_BINDING_KEY = "TO-DLQ"
}