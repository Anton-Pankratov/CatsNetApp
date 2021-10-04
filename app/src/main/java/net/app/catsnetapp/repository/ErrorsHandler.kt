package net.app.catsnetapp.repository

import net.app.catsnetapp.data.network.Failure
import retrofit2.HttpException
import java.net.UnknownHostException

object ErrorsHandler {

    fun handle(error: Throwable): Failure<String?> {
        return Failure(
            when (error) {
                is UnknownHostException -> "Service is temporary unavailable"
                is HttpException -> {
                    Error.values().find { it.code == error.code() }?.message
                }
                else -> "Unknown error"
            }
        )
    }
}

enum class Error(val code: Int, val message: String) {

    /** Client Errors */

    BAD_REQUEST(400, "Incorrect parameters"),
    UNAUTHORIZED(401, "Access requires authentication"),
    PAYMENT_REQUIRED(402, "Payment required"),
    FORBIDDEN(403, "Insufficient rights"),
    NOT_FOUND(404, "Resource not found"),
    METHOD_NOT_ALLOWED(405, "Method not allowed"),
    NOT_ACCEPTABLE(406, "Invalid header data"),
    PROXY_AUTHENTICATION_REQUIRED(407, "Access requires authentication on proxy-server"),
    REQUEST_TIMEOUT(408, "Request timed out"),
    CONFLICT(409, "Error when sharing a resource "),
    GONE(410, "The resource at this address is no longer available"),
    LENGTH_REQUIRED(411, "Resource size not specified for server"),
    PRECONDITION_FAILED(412, "The request does not match the preset values"),
    PAYLOAD_TOO_LARGE(413, "The message size is too large"),
    URI_TOO_LONG(414, "URIs length is too long"),
    UNSUPPORTED_MEDIA_TYPE(415, "This data format is not supported"),
    RANGE_NOT_SATISFIABLE(416, "The range of the request does not meet the requirements "),
    EXPECTATION_FAILED(417, "Error processing request"),
    I_AM_TEAPOT(418, "This request could not be processed"),
    AUTHENTICATION_TIMEOUT(419, "Time for authentication is out"),
    MISDIRECTED_REQUEST(421, "Forwarded request cannot receive a response"),
    UNPROCESSABLE_ENTITY(422, "A logical error occurred while processing the request "),
    LOCKED(423, "Request locked"),
    FAILED_DEPENDENCY(424, "Another related operation failed"),
    TOO_EARLY(425, "This request cannot be processed at the moment"),
    UPGRADE_REQUIRED(
        426,
        "It is necessary to update the protocol for communication with the server"
    ),
    PRECONDITION_REQUIRED(428, "Conditions for processing the request not found"),
    TOO_MANY_REQUESTS(429, "Too many requests made"),
    REQUEST_HEADER_FIELDS_TOO_LARGE(431, "Some query parameters are too large"),
    RETRY_WITH(449, "Insufficient information provided"),
    UNAVAILABLE_FOR_LEGAL_REASONS(451, "Access to the resource is closed"),
    CLIENT_CLOSED_REQUEST(499, "The connection was closed while processing the request"),

    /** Server Errors */

    INTERNAL_SERVER_ERROR(500, "An error occurred from the server side"),
    NOT_IMPLEMENTED(501, "The server cannot process the request due to the lack of a method"),
    BAD_GATEWAY(502, "An error occurred from a third-party service"),
    SERVICE_UNAVAILABLE(503, "Server is temporarily unavailable"),
    GATEWAY_TIMEOUT(504, "Third-party service did not respond "),
    HTTP_VERSION_NOT_SUPPORTED(505, "HTTPs protocol version not supported "),
    VARIANT_ALSO_NEGOTIATES(506, "Error in request configuration"),
    INSUFFICIENT_STORAGE(507, "Not enough space to complete the current request"),
    LOOP_DETECTED(508, "Request forcibly canceled"),
    BANDWIDTH_LIMIT_EXCEEDED(509, "Traffic usage limit exceeded by request"),
    NOT_EXTENDED(510, "The requested extension is missing"),
    NETWORK_AUTHENTICATION_REQUIRED(511, "Network authentication required"),
    UNKNOWN_ERROR(520, "An unknown error has occurred"),
    WEB_SERVER_IS_DOWN(521, "Connection to web server is down"),
    CONNECTION_TIMED_OUT(522, "Connection timed out"),
    ORIGIN_IS_UNREACHABLE(523, "Unable to contact the web server"),
    A_TIMEOUT_OCCURRED(524, "The services have timed out to connect to the web service"),
    SSL_HANDSHAKE_FAILED(525, "Security check failed"),
    INVALID_SSL_CERTIFICATE(526, "Security certificate is invalid")
}