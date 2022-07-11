package me.brunofelix.cstvapp.data.api.response

import com.google.gson.annotations.SerializedName

data class TeamResponse(
    @SerializedName("")
    val id: Long?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("image_url")
    val imageUrl: String?,

    @SerializedName("players")
    val players: List<PlayerResponse>?
)
