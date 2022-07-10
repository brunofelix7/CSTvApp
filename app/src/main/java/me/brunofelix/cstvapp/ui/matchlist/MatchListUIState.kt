package me.brunofelix.cstvapp.ui.matchlist

import me.brunofelix.cstvapp.data.api.response.MatchResponse

sealed class MatchListUIState {
    object Initial: MatchListUIState()
    object Loading: MatchListUIState()
    class OnSuccess(val data: List<MatchResponse>): MatchListUIState()
    class OnError(val message: String): MatchListUIState()
}
