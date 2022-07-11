package me.brunofelix.cstvapp.ui.matchdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.brunofelix.cstvapp.data.api.repository.TeamRepository
import me.brunofelix.cstvapp.data.api.result.TeamResult
import me.brunofelix.cstvapp.util.AppProvider
import javax.inject.Inject

@HiltViewModel
class MatchDetailsViewModel @Inject constructor(
    private val repository: TeamRepository,
    private val dispatcher: CoroutineDispatcher,
    private val provider: AppProvider
) : ViewModel() {

    private val _uiStateFlow = MutableStateFlow<MatchDetailsUIState>(MatchDetailsUIState.Initial)
    val uiSateFlow: StateFlow<MatchDetailsUIState> get() = _uiStateFlow

    fun getTeam(teamOneId: Long, teamTwoId: Long) {
        _uiStateFlow.value = MatchDetailsUIState.Loading

        viewModelScope.launch(dispatcher) {
            when(val result = repository.getTeam(teamOneId, teamTwoId)) {
                is TeamResult.OnSuccess -> {
                    if (result.data != null && result.data.isNotEmpty()) {
                        _uiStateFlow.value = MatchDetailsUIState.OnSuccess(result.data)
                    } else {
                        _uiStateFlow.value = MatchDetailsUIState.OnError(result.message ?: "")
                    }
                }
                is TeamResult.OnNetworkError ->_uiStateFlow.value = MatchDetailsUIState.OnError(result.message)
                is TeamResult.OnTimeOutError -> _uiStateFlow.value = MatchDetailsUIState.OnError(result.message)
                is TeamResult.OnError -> _uiStateFlow.value = MatchDetailsUIState.OnError(result.message)
            }            
        }
    }
}
