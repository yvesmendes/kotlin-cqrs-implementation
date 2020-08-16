package br.com.tdc.cqrs.catalog.controller

import br.com.tdc.cqrs.catalog.domain.Catalog
import br.com.tdc.cqrs.catalog.dto.CatalogCartDTO
import br.com.tdc.cqrs.catalog.dto.CatalogCartResponseDTO
import br.com.tdc.cqrs.catalog.dto.CatalogDTO
import br.com.tdc.cqrs.catalog.services.CatalogService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("v1/catalog")
class CatalogController(val catalogService: CatalogService) {

    private val log = LoggerFactory.getLogger(javaClass)

    @PostMapping
    fun save(@RequestBody catalog: CatalogDTO): ResponseEntity<Catalog> {
        log.info("Add product: $catalog")
        return ResponseEntity.ok(catalogService.saveCatalog(catalog))
    }

    @PostMapping("/calculate")
    fun calculate(@RequestBody catalogItem: CatalogCartDTO): ResponseEntity<CatalogCartResponseDTO> {
        log.info("Calculate catalog: $catalogItem")
        return ResponseEntity.ok(catalogService.calculateCatalog(catalogItem))
    }

    @PutMapping
    fun alter(@RequestBody catalog: Catalog): ResponseEntity<Unit> {
        log.info("Alter product: $catalog")
        catalogService.updateProduct(catalog)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("{id}")
    fun delete(@PathVariable id: String): ResponseEntity<Unit> {
        log.info("Delete product with id: $id")
        val product = catalogService.getById(id)
        catalogService.deleteById(product.productId)
        return ResponseEntity.ok().build()
    }

    @GetMapping("{id}")
    fun get(@PathVariable id: String): ResponseEntity<Catalog> {
        log.info("Retrieve product with id: $id")
        return ResponseEntity.ok(catalogService.getById(id))
    }
}
