package me.brunofelix.cstvapp.data.api.repository

import me.brunofelix.cstvapp.data.api.ApiResult
import me.brunofelix.cstvapp.data.api.response.TeamResponse

interface TeamRepository {
    suspend fun getTeam(id: Long): ApiResult<TeamResponse?>
}
