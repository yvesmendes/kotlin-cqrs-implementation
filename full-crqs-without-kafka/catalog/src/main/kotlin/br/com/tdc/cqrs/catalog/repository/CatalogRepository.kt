package br.com.tdc.cqrs.catalog.repository

import br.com.tdc.cqrs.catalog.domain.Catalog
import org.springframework.data.repository.CrudRepository

interface CatalogRepository : CrudRepository<Catalog, String>
