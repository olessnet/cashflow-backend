package app.theblu.cashflow.cs.batteries.rocket.exception

import app.theblu.cashflow.cs.batteries.rocket.res.ApiError
import app.theblu.cashflow.cs.batteries.rocket.res.ApiErrorBuilder
import app.theblu.cashflow.cs.batteries.rocket.res.ErrorDetail

class NotYetImplementedException : AppException() {
    override fun response(): ApiError {
        return ApiErrorBuilder()
            .status(500)
            .error(ErrorDetail(message = "Something went wrong", developerMessage = "Not Yet Implemented"))
            .build()
    }
}