package me.brunofelix.cstvapp.data.api

import me.brunofelix.cstvapp.data.api.response.MatchResponse
import me.brunofelix.cstvapp.data.api.response.TeamResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("csgo/matches")
    suspend fun fetchMatches(): Response<List<MatchResponse>>

    @GET("teams")
    suspend fun getTeam(@Path("id") id: Long): Response<TeamResponse>
}
