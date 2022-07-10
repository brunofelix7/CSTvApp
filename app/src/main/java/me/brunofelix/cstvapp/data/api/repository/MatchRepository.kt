package me.brunofelix.cstvapp.data.api.repository

import me.brunofelix.cstvapp.data.api.ApiResult
import me.brunofelix.cstvapp.data.api.response.MatchResponse

interface MatchRepository {
    suspend fun fetchMatches() : ApiResult<List<MatchResponse>?>
}
