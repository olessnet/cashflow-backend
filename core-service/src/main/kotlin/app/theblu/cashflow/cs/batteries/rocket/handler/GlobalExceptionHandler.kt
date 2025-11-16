package app.theblu.cashflow.cs.batteries.rocket.handler

import app.theblu.cashflow.cs.batteries.rocket.exception.AppException
import app.theblu.cashflow.cs.batteries.rocket.res.ApiErrorBuilder
import app.theblu.cashflow.cs.batteries.rocket.res.ErrorDetail
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
class GlobalExceptionHandler {
    companion object {
        private val log: Logger = LoggerFactory.getLogger(this::class.java)
    }

    @ExceptionHandler(AppException::class)
    fun handleAppException(ex: AppException): ResponseEntity<Any> {
        return ex.responseEntity()
    }

    @ExceptionHandler(Exception::class)
    fun handleUnknownException(ex: Exception): ResponseEntity<Any> {
        log.error("ex" ,ex)
        if (ex.javaClass.getSimpleName() == "DataAccessResourceFailureException") {
            return handleDataAccessResourceFailureException(ex)
        }

        val errorDetail = ErrorDetail(message="Something went wrong", developerMessage=ex.message)
        val res = ApiErrorBuilder().status(500).error(errorDetail).build()
        return ResponseEntity<Any>(res, HttpStatusCode.valueOf(res.status))
    }

    fun handleDataAccessResourceFailureException(exception: Exception?): ResponseEntity<Any> {
        log.error("handleDataAccessResourceFailureException", exception)
        val errorDetail = ErrorDetail(message="upstream server error", developerMessage="database server not available")
        val res = ApiErrorBuilder().status(500).error(errorDetail).build()
        return ResponseEntity<Any>(res, HttpStatusCode.valueOf(res.status))
    }
}