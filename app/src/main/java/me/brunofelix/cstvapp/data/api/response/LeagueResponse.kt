package me.brunofelix.cstvapp.data.api.response

import com.google.gson.annotations.SerializedName

data class LeagueResponse(
    @SerializedName("id")
    val id: Long?,

    @SerializedName("image_url")
    val imageURL: String?,

    @SerializedName("name")
    val name: String?
)
