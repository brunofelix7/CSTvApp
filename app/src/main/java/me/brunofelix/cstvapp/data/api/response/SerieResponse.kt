package me.brunofelix.cstvapp.data.api.response

import com.google.gson.annotations.SerializedName

data class SerieResponse(
    @SerializedName("id")
    val id: Long?,

    @SerializedName("name")
    val name: String?,
)
