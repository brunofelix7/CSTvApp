package me.brunofelix.cstvapp.data.api.repository

import me.brunofelix.cstvapp.R
import me.brunofelix.cstvapp.data.api.ApiService
import me.brunofelix.cstvapp.data.api.response.TeamResponse
import me.brunofelix.cstvapp.data.api.result.TeamResult
import me.brunofelix.cstvapp.util.AppProvider
import timber.log.Timber
import java.net.SocketTimeoutException

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
                TeamResult.OnError<String>(responseTeamOne.message())
            }
            if (responseTeamTwo.isSuccessful && responseTeamTwo.body() != null) {
                teamsList.add(responseTeamTwo.body())
            } else {
                TeamResult.OnError<String>(responseTeamOne.message())
            }

            TeamResult.OnSuccess(teamsList)
        }

        catch (e: SocketTimeoutException) {
            Timber.e(e)
            TeamResult.OnTimeOutError(provider.res().getString(R.string.msg_timeout_error))
        } catch (e: Exception) {
            Timber.e(e)
            TeamResult.OnError(provider.res().getString(R.string.msg_general_error))
        }
    }
}
