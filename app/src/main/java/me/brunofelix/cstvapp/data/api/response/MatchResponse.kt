package me.brunofelix.cstvapp.data.api.response

import com.google.gson.annotations.SerializedName

data class MatchResponse(
    @SerializedName("id")
    val id: Long?,

    @SerializedName("league_id")
    val leagueId: Long?,

    @SerializedName("serie_id")
    val serieId: Long?,

    @SerializedName("tournament_id")
    val tournamentId: Long?,

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
)
