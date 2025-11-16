package app.theblu.cashflow.cs.batteries.rocket.exception

import app.theblu.cashflow.cs.batteries.rocket.res.ApiError
import app.theblu.cashflow.cs.batteries.rocket.res.ApiErrorBuilder
import app.theblu.cashflow.cs.batteries.rocket.res.ErrorDetail
import org.apache.logging.log4j.message.Message

class BusinessValidationException(
    val subErrors: List<ErrorDetail>
) : AppException() {

    override fun response(): ApiError {
        return ApiErrorBuilder()
            .status(422)
            .error(ErrorDetail(message = "Validation failed", developerMessage = "Static validation failed"))
            .subErrors(subErrors)
            .build()
    }

    companion object {
        fun withError(
            field: String? = null,
            message: String,
            developerMessage: String? = null
        ): BusinessValidationException {
            val errors = mutableListOf<ErrorDetail>()
            var error = ErrorDetail(field = field, message = message, developerMessage = developerMessage)
            errors.add(error)
            return BusinessValidationException(errors)
        }
    }
}