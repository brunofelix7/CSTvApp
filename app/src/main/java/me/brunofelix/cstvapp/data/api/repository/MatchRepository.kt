package me.brunofelix.cstvapp.data.api.repository

import me.brunofelix.cstvapp.data.api.result.MatchResult
import me.brunofelix.cstvapp.data.api.response.MatchResponse

interface MatchRepository {
    suspend fun fetchMatches(): MatchResult<List<MatchResponse>?>
}
