package net.app.catsnetapp.network

class ErrorsHandler {
}

enum class Error(val code: Int, val message: String) {

    /** Client Errors */

    BAD_REQUEST(400,"Incorrect parameters"),
    UNAUTHORIZED(401, "Access requires authentication"),
    PAYMENT_REQUIRED(402, "Payment required"),
    FORBIDDEN(403, "Insufficient rights "),
    NOT_FOUND(404, "Resource not found"),
    METHOD_NOT_ALLOWED(405, "Method not allowed"),
    NOT_ACCEPTABLE(406, "Invalid header data"),
    PROXY_AUTHENTICATION_REQUIRED(407, "Access requires authentication on proxy-server"),
    REQUEST_TIMEOUT(408, ""),
    CONFLICT(409, ""),
    GONE(410, ""),
    LENGTH_REQUIRED(411, ""),
    PRECONDITION_FAILED(412, ""),
    PAYLOAD_TOO_LARGE(413, ""),
    URI_TOO_LONG(414, ""),
    UNSUPPORTED_MEDIA_TYPE(415, ""),
    RANGE_NOT_SATISFIABLE(416, ""),
    EXPECTATION_FAILED(417, ""),
    I_AM_TEAPOT(418, ""),
    AUTHENTICATION_TIMEOUT(419, ""),
    MISDIRECTED_REQUEST(421, ""),
    UNPROCESSABLE_ENTITY(422, ""),
    LOCKED(423, ""),
    FAILED_DEPENDENCY(424, ""),
    TOO_EARLY(425, ""),
    UPGRADE_REQUIRED(426, ""),
    PRECONDITION_REQUIRED(428, ""),
    TOO_MANY_REQUESTS(429, ""),
    REQUEST_HEADER_FIELDS_TOO_LARGE(431, ""),
    RETRY_WITH(449, ""),
    UNAVAILABLE_FOR_LEGAL_REASONS(451, ""),
    CLIENT_CLOSED_REQUEST(499, ""),

    /** Server Errors */

    INTERNAL_SERVER_ERROR(500, ""),
    NOT_IMPLEMENTED(501, ""),
    BAD_GATEWAY(502, ""),
    SERVICE_UNAVAILABLE(503, ""),
    GATEWAY_TIMEOUT(504, ""),
    HTTP_VERSION_NOT_SUPPORTED(505, ""),
    VARIANT_ALSO_NEGOTIATES(506,""),
    INSUFFICIENT_STORAGE(507, ""),
    LOOP_DETECTED(508, ""),
    BANDWIDTH_LIMIT_EXCEEDED(509, ""),
    NOT_EXTENDED(510, ""),
    NETWORK_AUTHENTICATION_REQUIRED(511,""),
    UNKNOWN_ERROR(520, ""),
    WEB_SERVER_IS_DOWN(521, ""),
    CONNECTION_TIMED_OUT(522, ""),
    ORIGIN_IS_UNREACHABLE(523, ""),
    A_TIMEOUT_OCCURRED(524, ""),
    SSL_HANDSHAKE_FAILED(525, ""),
    INVALID_SSL_CERTIFICATE(526, "")
}