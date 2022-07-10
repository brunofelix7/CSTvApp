package me.brunofelix.cstvapp.data.api.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class OpponentListResponse(

    @SerializedName("opponent")
    val opponent: OpponentResponse?
) : Parcelable
