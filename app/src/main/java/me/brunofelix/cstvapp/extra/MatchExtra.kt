package me.brunofelix.cstvapp.extra

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MatchExtra(
    val id: Long?,
    val leagueName: String?,
    val serieName: String?,
    val scheduledAt: String?,
    val teamOneId: Long?,
    val teamTwoId: Long?,
    val teamOneName: String?,
    val teamTwoName: String?,
    val teamOneImageUrl: String?,
    val teamTwoImageUrl: String?,
) : Parcelable
