package me.brunofelix.cstvapp.data.api

sealed class ApiResult<T>(val data: T?, val message: String?) {
    class OnSuccess<T>(data: T) : ApiResult<T>(data, null)
    class OnError<T>(message: String) : ApiResult<T>(null, message)
    class OnNetworkError<T>(message: String) : ApiResult<T>(null, message)
    class OnTimeOutError<T>(message: String) : ApiResult<T>(null, message)
}
