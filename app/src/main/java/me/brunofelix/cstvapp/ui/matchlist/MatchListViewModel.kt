package me.brunofelix.cstvapp.ui.matchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.brunofelix.cstvapp.data.api.result.MatchResult
import me.brunofelix.cstvapp.data.api.repository.MatchRepository
import me.brunofelix.cstvapp.util.AppProvider
import javax.inject.Inject

@HiltViewModel
class MatchListViewModel @Inject constructor(
    private val repository: MatchRepository,
    private val dispatcher: CoroutineDispatcher,
    private val provider: AppProvider
): ViewModel() {

    private val _uiStateFlow = MutableStateFlow<MatchListUIState>(MatchListUIState.Initial)
    val uiSateFlow: StateFlow<MatchListUIState> get() = _uiStateFlow

    fun fetchMatches() {
        _uiStateFlow.value = MatchListUIState.Loading

        viewModelScope.launch(dispatcher) {
            when(val result = repository.fetchMatches()) {
                is MatchResult.OnSuccess -> {
                    if (result.data == null) {
                        _uiStateFlow.value = MatchListUIState.OnError(result.message ?: "")
                    } else {
                        _uiStateFlow.value = MatchListUIState.OnSuccess(result.data)
                    }
                }
                is MatchResult.OnNetworkError -> _uiStateFlow.value = MatchListUIState.OnError(result.message)
                is MatchResult.OnTimeOutError -> _uiStateFlow.value = MatchListUIState.OnError(result.message)
                is MatchResult.OnError -> _uiStateFlow.value = MatchListUIState.OnError(result.message)
            }
        }
    }
}
