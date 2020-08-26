package br.com.tdc.cqrs.catalog.exceptions

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest


@RestControllerAdvice
class ExceptionResolver {

    @ExceptionHandler(NotFoundException::class)
    fun handleNoHandlerFound(e: NotFoundException, request: WebRequest): ResponseEntity<Unit> {
        return ResponseEntity.notFound().build()
    }

}