package me.brunofelix.cstvapp.data.api.response

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("error")
    val message: String?
)
