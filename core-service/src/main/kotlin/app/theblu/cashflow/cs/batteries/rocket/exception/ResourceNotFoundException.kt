package app.theblu.cashflow.cs.batteries.rocket.exception

import app.theblu.cashflow.cs.batteries.rocket.res.ApiError
import app.theblu.cashflow.cs.batteries.rocket.res.ApiErrorBuilder
import app.theblu.cashflow.cs.batteries.rocket.res.ErrorDetail

class ResourceNotFoundException : AppException() {
    override fun response(): ApiError {
        return ApiErrorBuilder()
            .status(404)
            .error(ErrorDetail(message = "Resource not found"))
            .build()
    }
}