package me.brunofelix.cstvapp.data.api.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class OpponentResponse(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("image_url")
    val imageUrl: String?,

    @SerializedName("name")
    val name: String?
) : Parcelable
