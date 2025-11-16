package app.theblu.cashflow.cs.batteries.rocket.handler

import app.theblu.cashflow.cs.batteries.rocket.res.ApiErrorBuilder
import app.theblu.cashflow.cs.batteries.rocket.res.ErrorDetail
import com.fasterxml.jackson.databind.exc.MismatchedInputException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.ConversionNotSupportedException
import org.springframework.beans.TypeMismatchException
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatusCode
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.http.converter.HttpMessageNotWritableException
import org.springframework.lang.Nullable
import org.springframework.util.StringUtils
import org.springframework.validation.FieldError
import org.springframework.web.*
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingPathVariableException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.ServletRequestBindingException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.HttpServerErrorException
import org.springframework.web.client.ResourceAccessException
import org.springframework.web.client.UnknownHttpStatusCodeException
import org.springframework.web.context.request.WebRequest
import org.springframework.web.context.request.async.AsyncRequestTimeoutException
import org.springframework.web.multipart.support.MissingServletRequestPartException
import org.springframework.web.servlet.NoHandlerFoundException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import org.springframework.web.servlet.resource.NoResourceFoundException

@RestControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE - 1)
class SpringDefaultExceptionHandler : ResponseEntityExceptionHandler() {
    companion object {
        private val log: Logger = LoggerFactory.getLogger(this::class.java)
    }


    @Nullable
    override fun handleNoResourceFoundException(
        ex: NoResourceFoundException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any> {
        val errorDetail = ErrorDetail(message="Path not found", developerMessage="Path not found")
        return ApiErrorBuilder().status(404).error(errorDetail).buildResponseEntity()
    }

    // when called unsupported method on existing rest endpoint
    @Nullable
    override fun handleHttpRequestMethodNotSupported(
        ex: HttpRequestMethodNotSupportedException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any> {
        var supportedMethods = ""
        if (supportedMethods != null) {
            supportedMethods = "[ " + StringUtils.arrayToDelimitedString(ex.getSupportedMethods(), ", ") + " ]"
        }
        val msg = "Request method '" + ex.method + "' is not supported."
        val devMsg = "$msg Supported Endpoints are: $supportedMethods"

        val errorDetail = ErrorDetail(message=msg, developerMessage=devMsg)
        return ApiErrorBuilder().status(405).error(errorDetail).buildResponseEntity()
    }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any> {
        val errorDetail = ErrorDetail(message="Validation failed", developerMessage=ex.message)
        val subErrors = mutableListOf<ErrorDetail>()
        for (error in ex.getBindingResult().getAllErrors()) {
            if (error is FieldError) {
                val detail =
                    ErrorDetail(message = error.defaultMessage ?: "", developerMessage = "", field = error.field)
                subErrors.add(detail)
            }
        }
        return ApiErrorBuilder().status(400).error(errorDetail).subErrors(subErrors).buildResponseEntity()
    }

    @ExceptionHandler(HttpClientErrorException::class)
    protected fun handleHttpClientErrorException(ex: HttpClientErrorException): ResponseEntity<Any> {
        val errorDetail = ErrorDetail(message="something went wrong", developerMessage="upstream: " + ex.message)
        return ApiErrorBuilder().status(500).error(errorDetail).buildResponseEntity()

    }

    @ExceptionHandler(HttpServerErrorException::class)
    protected fun handleHttpServerErrorException(ex: HttpServerErrorException): ResponseEntity<Any> {
        val errorDetail = ErrorDetail(message="something went wrong", developerMessage="upstream: " + ex.message)
        return ApiErrorBuilder().status(500).error(errorDetail).buildResponseEntity()
    }

    @ExceptionHandler(UnknownHttpStatusCodeException::class)
    protected fun handleUnknownHttpStatusCodeException(exception: UnknownHttpStatusCodeException?): ResponseEntity<Any> {
        val errorDetail = ErrorDetail(message="something went wrong", developerMessage="unknown status code: " + exception?.statusCode)
        return ApiErrorBuilder().status(500).error(errorDetail).buildResponseEntity()

    }

    @ExceptionHandler(ResourceAccessException::class)
    protected fun handleResourceAccessException(exception: ResourceAccessException?): ResponseEntity<Any> {
        val errorDetail = ErrorDetail(message="upstream error. upstream service may not be available right now")
        return ApiErrorBuilder().status(500).error(errorDetail).buildResponseEntity()
    }

    @Nullable
    override fun handleHttpMediaTypeNotSupported(
        ex: HttpMediaTypeNotSupportedException, headers: HttpHeaders, status: HttpStatusCode, request: WebRequest
    ): ResponseEntity<Any>? {
        val errorDetail = ErrorDetail(message="Invalid request content type")
        return ApiErrorBuilder().status(415).error(errorDetail).buildResponseEntity()
    }

    @Nullable
    override fun handleHttpMediaTypeNotAcceptable(
        ex: HttpMediaTypeNotAcceptableException, headers: HttpHeaders, status: HttpStatusCode, request: WebRequest
    ): ResponseEntity<Any?>? {
        return handleExceptionInternal(ex, null, headers, status, request)
    }

    @Nullable
    override fun handleMissingPathVariable(
        ex: MissingPathVariableException, headers: HttpHeaders, status: HttpStatusCode, request: WebRequest
    ): ResponseEntity<Any?>? {
        return handleExceptionInternal(ex, null, headers, status, request)
    }

    @Nullable
    override fun handleMissingServletRequestParameter(
        ex: MissingServletRequestParameterException, headers: HttpHeaders, status: HttpStatusCode, request: WebRequest
    ): ResponseEntity<Any> {
        val errorDetail = ErrorDetail(message="Invalid Request Content", developerMessage=ex.message)
        val subErrors = mutableListOf<ErrorDetail>()
        subErrors.add(ErrorDetail(message = "invalid data or missin data", field = ex.parameterName))
        return ApiErrorBuilder().status(400).error(errorDetail).subErrors(subErrors).buildResponseEntity()
    }

    @Nullable
    override fun handleMissingServletRequestPart(
        ex: MissingServletRequestPartException, headers: HttpHeaders, status: HttpStatusCode, request: WebRequest
    ): ResponseEntity<Any?>? {
        return handleExceptionInternal(ex, null, headers, status, request)
    }

    @Nullable
    override fun handleServletRequestBindingException(
        ex: ServletRequestBindingException, headers: HttpHeaders, status: HttpStatusCode, request: WebRequest
    ): ResponseEntity<Any?>? {
        return handleExceptionInternal(ex, null, headers, status, request)
    }

    @Nullable
    override fun handleNoHandlerFoundException(
        ex: NoHandlerFoundException, headers: HttpHeaders, status: HttpStatusCode, request: WebRequest
    ): ResponseEntity<Any> {
        val errorDetail = ErrorDetail(message="path not found or rest endpoint not found")
        return ApiErrorBuilder().status(404).error(errorDetail).buildResponseEntity()
    }

    @Nullable
    override fun handleAsyncRequestTimeoutException(
        ex: AsyncRequestTimeoutException, headers: HttpHeaders, status: HttpStatusCode, request: WebRequest
    ): ResponseEntity<Any?>? {
        return handleExceptionInternal(ex, null, headers, status, request)
    }

    @Nullable
    override fun handleErrorResponseException(
        ex: ErrorResponseException, headers: HttpHeaders, status: HttpStatusCode, request: WebRequest
    ): ResponseEntity<Any?>? {
        return handleExceptionInternal(ex, null, headers, status, request)
    }

    @Nullable
    override fun handleConversionNotSupported(
        ex: ConversionNotSupportedException, headers: HttpHeaders, status: HttpStatusCode, request: WebRequest
    ): ResponseEntity<Any?>? {
        val args = arrayOf<Any?>(ex.getPropertyName(), ex.getValue())
        val defaultDetail = "Failed to convert '" + args[0] + "' with value: '" + args[1] + "'"
        val body: ProblemDetail = createProblemDetail(ex, status, defaultDetail, null, args, request)

        return handleExceptionInternal(ex, body, headers, status, request)
    }

    @Nullable
    override fun handleTypeMismatch(
        ex: TypeMismatchException, headers: HttpHeaders, status: HttpStatusCode, request: WebRequest
    ): ResponseEntity<Any?>? {
        val args = arrayOf<Any?>(ex.getPropertyName(), ex.getValue())
        val defaultDetail = "Failed to convert '" + args[0] + "' with value: '" + args[1] + "'"
        val messageCode = ErrorResponse.getDefaultDetailMessageCode(TypeMismatchException::class.java, null)
        val body: ProblemDetail = createProblemDetail(ex, status, defaultDetail, messageCode, args, request)

        return handleExceptionInternal(ex, body, headers, status, request)
    }

    override fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any> {
        val body: ProblemDetail = createProblemDetail(ex, status, "Failed to read request", null, null, request)
        val errorDetail = ErrorDetail(message="Validation failed", developerMessage=ex.message)
        val subErrors = mutableListOf<ErrorDetail>()

        val cause = ex.cause
        if (cause is MismatchedInputException) {
            val fieldName = cause.getPath().get(0).getFieldName()
            val ed = ErrorDetail(field = fieldName, message = "invalid data", developerMessage = cause.localizedMessage)
            subErrors.add(ed)
        }

        return ApiErrorBuilder().status(400).error(errorDetail).subErrors(subErrors).buildResponseEntity()
    }

    @Nullable
    override fun handleHttpMessageNotWritable(
        ex: HttpMessageNotWritableException, headers: HttpHeaders, status: HttpStatusCode, request: WebRequest
    ): ResponseEntity<Any?>? {
        val body: ProblemDetail = createProblemDetail(ex, status, "Failed to write request", null, null, request)
        return handleExceptionInternal(ex, body, headers, status, request)
    }
}