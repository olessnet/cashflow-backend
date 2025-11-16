package app.theblu.cashflow.cs.batteries.rocket.res

import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity

class ApiErrorBuilder {
    private var status = 0

    private var error: ErrorDetail? = null
    private var subErrors: MutableList<ErrorDetail> = mutableListOf()

    fun status(status: Int): ApiErrorBuilder {
        this.status = status
        return this
    }

    fun error(error: ErrorDetail): ApiErrorBuilder {
        this.error = error
        return this
    }

    fun subErrors(subError: ErrorDetail): ApiErrorBuilder {
        this.subErrors.add(subError)
        return this
    }

    fun subErrors(subErrors: List<ErrorDetail>): ApiErrorBuilder {
        this.subErrors.addAll(subErrors)
        return this
    }

    fun build(): ApiError {
        if (status == 0) throw RuntimeException("response status code is 0. Please set the data or status code")
        if (error == null) throw RuntimeException("error detail is null. please set error detail")

        return ApiError(
            status = status,
            error = error!!,
            subErrors = subErrors
        )
    }

    fun buildResponseEntity(): ResponseEntity<Any> {
        val res = this.build()
        return ResponseEntity<Any>(res, HttpStatusCode.valueOf(res.status))
    }
}