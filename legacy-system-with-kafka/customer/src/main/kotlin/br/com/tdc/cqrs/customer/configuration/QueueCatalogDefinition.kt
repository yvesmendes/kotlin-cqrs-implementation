package br.com.tdc.cqrs.customer.configuration

object QueueCatalogDefinition {
    const val CATALOG_QUEUE_ADD = "CATALOG-QUEUE"
    const val ORDER_QUEUE_INVALID = "ORDER-QUEUE-INVALID"
    const val CATALOG_EXCHANGE = "catalogExchange"
    const val CATALOG_QUEUE_ADD_KEY = "modify"
    const val CATALOG_QUEUE_PLACED_KEY = "placed"
    const val ORDER_QUEUE_INVALID_KEY = "invalid"
    const val DLQ_EXCHANGE = "DLQ-CATALOG-EXCHANGE"
    const val DLQ_QUEUE = "DLQ-CATALOG-QUEUE"
    const val DLQ_BINDING_KEY = "TO-DLQ"

    const val CATALOG_QUEUE_PLACED = "CATALOG-QUEUE-PLACED"
}