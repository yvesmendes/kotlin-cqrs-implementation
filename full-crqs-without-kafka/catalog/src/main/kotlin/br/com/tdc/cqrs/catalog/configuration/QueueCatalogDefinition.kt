package br.com.tdc.cqrs.catalog.configuration

object QueueCatalogDefinition {
    const val CATALOG_EXCHANGE = "catalogExchange"
    const val CATALOG_QUEUE_ADD_KEY = "modify"
    const val CATALOG_QUEUE_PLACED_KEY = "placed"
    const val ORDER_QUEUE_INVALID_KEY = "invalid"
    const val CONSUMER_ORDER_QUEUE = "ORDER-QUEUE"
    const val CONSUMER_ORDER_REVERT_QUEUE = "ACCOUNT-QUEUE-INVALID"
}