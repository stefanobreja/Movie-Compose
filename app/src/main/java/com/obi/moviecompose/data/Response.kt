package com.obi.moviecompose.data

sealed class Response<T>(data: T? = null, message: String? = null) {
    class Success<T>(data: T?) : Response<T>(data)
    class Error<T>(message: String?, data: T? = null) : Response<T>(data, message)
}
