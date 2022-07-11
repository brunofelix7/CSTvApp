package me.brunofelix.cstvapp.data.api.result

sealed class MatchResult<T>(val data: T?, val message: String?) {
    class OnSuccess<T>(data: T) : MatchResult<T>(data, null)
    class OnError<T>(message: String) : MatchResult<T>(null, message)
    class OnNetworkError<T>(message: String) : MatchResult<T>(null, message)
    class OnTimeOutError<T>(message: String) : MatchResult<T>(null, message)
}
