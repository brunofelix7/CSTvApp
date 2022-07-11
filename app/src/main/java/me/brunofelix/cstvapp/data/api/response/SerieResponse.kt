package me.brunofelix.cstvapp.data.api.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SerieResponse(
    @SerializedName("id")
    val id: Long?,

    @SerializedName("name")
    val name: String?,
) : Parcelable
