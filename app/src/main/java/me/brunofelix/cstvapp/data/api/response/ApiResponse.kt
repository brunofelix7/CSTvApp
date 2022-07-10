package me.brunofelix.cstvapp.data.api.response

import com.google.gson.annotations.SerializedName

data class ApiResponse (
    val matches: MatchResponse?,
    val message: String?,
    val error: String?
)