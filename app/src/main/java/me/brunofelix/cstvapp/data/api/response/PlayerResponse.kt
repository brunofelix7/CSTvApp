package me.brunofelix.cstvapp.data.api.response

import com.google.gson.annotations.SerializedName

data class PlayerResponse(
    @SerializedName("id")
    val id: Long?,

    @SerializedName("name")
    val nickname: String?,

    @SerializedName("first_name")
    val firstName: String?,

    @SerializedName("last_name")
    val lastName: String?,

    @SerializedName("image_url")
    val imageUrl: String?,
)
