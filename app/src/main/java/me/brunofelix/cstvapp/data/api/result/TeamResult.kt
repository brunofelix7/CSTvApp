package me.brunofelix.cstvapp.data.api.result

sealed class TeamResult<T>(val data: List<T>?, val message: String?) {
    class OnSuccess<T>(data: List<T>) : TeamResult<T>(data, null)
    class OnError<T>(message: String) : TeamResult<T>(null, message)
    class OnNetworkError<T>(message: String) : TeamResult<T>(null, message)
    class OnTimeOutError<T>(message: String) : TeamResult<T>(null, message)
}
