package me.brunofelix.cstvapp.data.api.repository

import me.brunofelix.cstvapp.R
import me.brunofelix.cstvapp.data.api.ApiResult
import me.brunofelix.cstvapp.data.api.ApiService
import me.brunofelix.cstvapp.data.api.response.TeamResponse
import me.brunofelix.cstvapp.util.AppProvider
import timber.log.Timber
import java.net.SocketTimeoutException

class TeamRepositoryImpl constructor(
    private val api: ApiService,
    private val provider: AppProvider
) : TeamRepository {

    override suspend fun getTeam(id: Long): ApiResult<TeamResponse?> {
        return try {
            val response = api.getTeam(id)

            if (response.isSuccessful && response.body() != null) {
                ApiResult.OnSuccess(response.body())
            } else {
                ApiResult.OnError(response.message() ?: "")
            }
        } catch (e: SocketTimeoutException) {
            Timber.e(e)
            ApiResult.OnTimeOutError(provider.res().getString(R.string.msg_timeout_error))
        } catch (e: Exception) {
            Timber.e(e)
            ApiResult.OnError(provider.res().getString(R.string.msg_general_error))
        }
    }
}
