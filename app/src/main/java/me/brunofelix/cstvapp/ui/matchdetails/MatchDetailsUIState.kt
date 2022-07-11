package me.brunofelix.cstvapp.ui.matchdetails

import me.brunofelix.cstvapp.data.api.response.TeamResponse

sealed class MatchDetailsUIState {
    object Initial: MatchDetailsUIState()
    object Loading: MatchDetailsUIState()
    class OnSuccess(val data: TeamResponse): MatchDetailsUIState()
    class OnError(val message: String): MatchDetailsUIState()
}
