package me.brunofelix.cstvapp.data.api.repository

import me.brunofelix.cstvapp.R
import me.brunofelix.cstvapp.data.api.result.MatchResult
import me.brunofelix.cstvapp.data.api.ApiService
import me.brunofelix.cstvapp.data.api.response.MatchResponse
import me.brunofelix.cstvapp.util.AppProvider
import timber.log.Timber
import java.net.SocketTimeoutException
import javax.inject.Inject

class MatchRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val provider: AppProvider
): MatchRepository {

    override suspend fun fetchMatches(): MatchResult<List<MatchResponse>?> {
        return try {
            val response = api.fetchMatches()

            if (response.isSuccessful && response.body() != null) {
                MatchResult.OnSuccess(response.body())
            } else {
                MatchResult.OnError(response.message() ?: "")
            }
        } catch (e: SocketTimeoutException) {
            Timber.e(e)
            MatchResult.OnTimeOutError(provider.res().getString(R.string.msg_timeout_error))
        } catch (e: Exception) {
            Timber.e(e)
            MatchResult.OnError(provider.res().getString(R.string.msg_general_error))
        }
    }
}
