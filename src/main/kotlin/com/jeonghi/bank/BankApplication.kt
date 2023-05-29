package com.jeonghi.bank

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan(
    value = ["com.jeonghi.bank.application.configuration"],
)
class BankApplication

fun main(args: Array<String>) {
    runApplication<BankApplication>(*args)
}
