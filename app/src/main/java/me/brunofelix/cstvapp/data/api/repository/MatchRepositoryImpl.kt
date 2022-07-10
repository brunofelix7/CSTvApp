package me.brunofelix.cstvapp.data.api.repository

import me.brunofelix.cstvapp.data.api.ApiResult
import me.brunofelix.cstvapp.data.api.ApiService
import me.brunofelix.cstvapp.data.api.response.MatchResponse
import timber.log.Timber
import javax.inject.Inject

class MatchRepositoryImpl @Inject constructor(
    private val api: ApiService
): MatchRepository {

    override suspend fun fetchMatches(): ApiResult<MatchResponse?> {
        return try {
            val response = api.fetchMatches()

            Timber.d(response.body().toString())

            if (response.isSuccessful && response.body() != null) {
                ApiResult.OnSuccess(response.body()?.get(0)?.matches)
            } else {
                ApiResult.OnError(response.body()?.get(0)?.error ?: "")
            }
        } catch (e: Exception) {
            Timber.e(e)
            ApiResult.OnError(e.message ?: "An error ocurred")
        }
    }
}
