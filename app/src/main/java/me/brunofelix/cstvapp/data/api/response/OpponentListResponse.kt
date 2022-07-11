package me.brunofelix.cstvapp.data.api.response

import com.google.gson.annotations.SerializedName

data class OpponentListResponse(

    @SerializedName("opponent")
    val opponent: OpponentResponse?
)
