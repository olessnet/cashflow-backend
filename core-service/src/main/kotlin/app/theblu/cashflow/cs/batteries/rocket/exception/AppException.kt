package app.theblu.cashflow.cs.batteries.rocket.exception

import app.theblu.cashflow.cs.batteries.rocket.res.ApiError
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity

abstract class AppException() : RuntimeException() {
    abstract fun response(): ApiError
    fun responseEntity(): ResponseEntity<Any> {
        val res = response()
        return ResponseEntity<Any>(res, HttpStatusCode.valueOf(res.status))
    }
}