package me.brunofelix.cstvapp.data.api.repository

import com.google.gson.Gson
import me.brunofelix.cstvapp.R
import me.brunofelix.cstvapp.data.api.ApiService
import me.brunofelix.cstvapp.data.api.response.ErrorResponse
import me.brunofelix.cstvapp.data.api.response.TeamResponse
import me.brunofelix.cstvapp.data.api.result.MatchResult
import me.brunofelix.cstvapp.data.api.result.TeamResult
import me.brunofelix.cstvapp.util.AppProvider
import timber.log.Timber
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class TeamRepositoryImpl constructor(
    private val api: ApiService,
    private val provider: AppProvider
) : TeamRepository {

    override suspend fun getTeam(teamOneId: Long, teamTwoId: Long): TeamResult<TeamResponse?> {
        return try {
            val teamsList = mutableListOf<TeamResponse?>()
            val responseTeamOne = api.getTeam(teamOneId)
            val responseTeamTwo = api.getTeam(teamTwoId)

            if (responseTeamOne.isSuccessful && responseTeamOne.body() != null) {
                teamsList.add(responseTeamOne.body())
            } else {
                val error = Gson().fromJson(responseTeamOne.errorBody()?.string(), ErrorResponse::class.java)
                TeamResult.OnError<String>("${responseTeamOne.code()} - ${error.message}")
            }
            if (responseTeamTwo.isSuccessful && responseTeamTwo.body() != null) {
                teamsList.add(responseTeamTwo.body())
            } else {
                val error = Gson().fromJson(responseTeamTwo.errorBody()?.string(), ErrorResponse::class.java)
                TeamResult.OnError<String>("${responseTeamTwo.code()} - ${error.message}")
            }

            TeamResult.OnSuccess(teamsList)
        } catch (e: UnknownHostException) {
            Timber.e(e)
            TeamResult.OnNetworkError(provider.res().getString(R.string.msg_connection_error))
        } catch (e: SocketTimeoutException) {
            Timber.e(e)
            TeamResult.OnTimeOutError(provider.res().getString(R.string.msg_timeout_error))
        } catch (e: Exception) {
            Timber.e(e)
            TeamResult.OnError(provider.res().getString(R.string.msg_general_error))
        }
    }
}
