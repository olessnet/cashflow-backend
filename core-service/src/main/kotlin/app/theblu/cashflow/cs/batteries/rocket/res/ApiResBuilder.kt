package app.theblu.cashflow.cs.batteries.rocket.res

import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import java.util.*

object ApiResBuilder {
    fun buildRes(data: Any? = null): ResponseEntity<Any> {
        return buildRes(data, null)
    }

    fun buildRes(data: Any? = null, status: Int? = null): ResponseEntity<Any> {
        var resBody: Any? = null
        var resStatus: Int? = null

        if (data == null && status != null) {
            resStatus = status
        } else if (data is Optional<*> && data.isPresent) {
            resBody = data.get()
            resStatus = status ?: 200
        } else if (data is Optional<*> && data.isEmpty) {
            resStatus = status ?: 404
        } else if (data is MutableCollection<*>) {
            resBody = data
            resStatus = status ?: 200
        } else if (data != null) {
            resBody = data
            resStatus = status ?: 200
        } else {
            resStatus = status ?: 404
        }
        return ResponseEntity<Any>(resBody, HttpStatusCode.valueOf(resStatus))
    }
}