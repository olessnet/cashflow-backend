package app.theblu.cashflow.cs.batteries.rocket.res

import com.fasterxml.jackson.annotation.JsonInclude

data class ApiError(
    val status: Int,
    val error: ErrorDetail,
    val subErrors: List<ErrorDetail>
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ErrorDetail(
    val field: String? = null,
    val message: String,
    val developerMessage: String? = null
)