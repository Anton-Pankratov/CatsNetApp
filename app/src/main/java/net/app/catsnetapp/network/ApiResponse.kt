package net.app.catsnetapp.network

sealed class ApiResponse<out S, out E>

class Success<S>(val data: S) : ApiResponse<S, Nothing>()

class Failure<E>(val error: E) : ApiResponse<Nothing, E>() {

}