package me.brunofelix.cstvapp.ui.matchdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.brunofelix.cstvapp.data.api.ApiResult
import me.brunofelix.cstvapp.data.api.repository.TeamRepository
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

    fun getTeam(id: Long) {
        _uiStateFlow.value = MatchDetailsUIState.Loading

        viewModelScope.launch(dispatcher) {
            when(val result = repository.getTeam(id)) {
                is ApiResult.OnSuccess -> {
                    if (result.data == null) {
                        _uiStateFlow.value = MatchDetailsUIState.OnError(result.message ?: "")
                    } else {
                        _uiStateFlow.value = MatchDetailsUIState.OnSuccess(result.data)
                    }
                }
                is ApiResult.OnNetworkError -> {
                    _uiStateFlow.value = MatchDetailsUIState.OnError(result.message ?: "")
                }
                is ApiResult.OnTimeOutError -> {
                    _uiStateFlow.value = MatchDetailsUIState.OnError(result.message ?: "")
                }
                is ApiResult.OnError -> {
                    _uiStateFlow.value = MatchDetailsUIState.OnError(result.message ?: "")
                }
            }            
        }
    }
}
