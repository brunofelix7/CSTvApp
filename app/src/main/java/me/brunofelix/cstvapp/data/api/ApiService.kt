package me.brunofelix.cstvapp.data.api

import me.brunofelix.cstvapp.data.api.response.ApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("csgo/matches")
    suspend fun fetchMatches(): Response<ApiResponse>
}