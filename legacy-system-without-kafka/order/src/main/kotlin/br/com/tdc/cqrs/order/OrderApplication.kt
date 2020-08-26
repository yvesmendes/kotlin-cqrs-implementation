package br.com.tdc.cqrs.order

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@SpringBootApplication
@EnableFeignClients
@EnableMongoRepositories
class OrderApplication

fun main(args: Array<String>) {
	runApplication<OrderApplication>(*args)
}
