package me.brunofelix.cstvapp.data.api.repository

import me.brunofelix.cstvapp.data.api.response.TeamResponse
import me.brunofelix.cstvapp.data.api.result.TeamResult

interface TeamRepository {
    suspend fun getTeam(teamOneId: Long, teamTwoId: Long): TeamResult<TeamResponse?>
}
