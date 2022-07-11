package me.brunofelix.cstvapp.data.api.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MatchResponse(
    @SerializedName("id")
    val id: Long?,

    @SerializedName("league_id")
    val leagueId: Int?,

    @SerializedName("serie_id")
    val serieId: Int?,

    @SerializedName("tournament_id")
    val tournamentId: Int?,

    @SerializedName("scheduled_at")
    val scheduledAt: String?,

    @SerializedName("status")
    val status: String?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("opponents")
    val opponents: List<OpponentListResponse>?,

    @SerializedName("league")
    val league: LeagueResponse?,

    @SerializedName("serie")
    val serie: SerieResponse?,
) : Parcelable
