package me.brunofelix.cstvapp.ui.matchlist

import me.brunofelix.cstvapp.data.api.response.MatchResponse

interface MatchListClickListener {
    fun onItemClick(match: MatchResponse)
}