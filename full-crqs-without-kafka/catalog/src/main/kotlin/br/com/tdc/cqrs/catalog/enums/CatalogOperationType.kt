package br.com.tdc.cqrs.catalog.enums

enum class CatalogOperationType(val operation : String) {
    CREATE("C"), UPDATE("U"), DELETE("D")
}