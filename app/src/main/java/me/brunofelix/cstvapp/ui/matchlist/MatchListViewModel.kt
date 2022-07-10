package me.brunofelix.cstvapp.ui.matchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.brunofelix.cstvapp.data.api.ApiResult
import me.brunofelix.cstvapp.data.api.repository.MatchRepository
import javax.inject.Inject

@HiltViewModel
class MatchListViewModel @Inject constructor(
    private val repository: MatchRepository,
    private val dispatcher: CoroutineDispatcher
): ViewModel() {

    private val _uiStateFlow = MutableStateFlow<MatchListUIState>(MatchListUIState.Initial)
    val uiSateFlow: StateFlow<MatchListUIState> get() = _uiStateFlow

    fun fetchMatches() {
        _uiStateFlow.value = MatchListUIState.Loading

        viewModelScope.launch(dispatcher) {
            when(val response = repository.fetchMatches()) {
                is ApiResult.OnSuccess -> {
                    if (response.data == null) {
                        _uiStateFlow.value = MatchListUIState.OnError(response.message ?: "")
                    } else {
                        _uiStateFlow.value = MatchListUIState.OnSuccess(response.data)
                    }
                }
                is ApiResult.OnError -> {
                    _uiStateFlow.value = MatchListUIState.OnError(response.message ?: "")
                }
            }
        }
    }
}
