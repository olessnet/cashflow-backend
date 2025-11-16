package app.theblu.cashflow.cs

import app.theblu.cashflow.cs.zone.recognizer.service.msgParser.CurrencyParser
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
class CashflowServiceApplication

fun main(args: Array<String>) {
    CurrencyParser.parse("")
	runApplication<CashflowServiceApplication>(*args)
}
