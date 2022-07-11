package me.brunofelix.cstvapp.data.api.response

import com.google.gson.annotations.SerializedName

data class OpponentResponse(
    @SerializedName("id")
    val id: Long?,

    @SerializedName("image_url")
    val imageUrl: String?,

    @SerializedName("name")
    val name: String?
)
