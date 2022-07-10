package me.brunofelix.cstvapp.ui.matchlist

import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import me.brunofelix.cstvapp.databinding.ActivityMatchListBinding
import me.brunofelix.cstvapp.ui.BaseActivity
import timber.log.Timber

@AndroidEntryPoint
class MatchListActivity : BaseActivity<ActivityMatchListBinding>(
    ActivityMatchListBinding::inflate
) {

    private val viewModel: MatchListViewModel by viewModels()
    private var uiStateJob: Job? = null

    override fun onStart() {
        super.onStart()
        viewModel.fetchMatches()
        collectData()
    }

    override fun onStop() {
        uiStateJob?.cancel()
        super.onStop()
    }

    private fun collectData() {
        uiStateJob = lifecycleScope.launchWhenCreated {
            viewModel.uiSateFlow.collect { uiState ->
                when(uiState) {
                    is MatchListUIState.Loading -> {
                        binding.progressBar.isVisible = true
                    }
                    is MatchListUIState.OnSuccess -> {
                        binding.progressBar.isVisible = false
                        Timber.d(uiState.data.toString())
                    }
                    is MatchListUIState.OnError -> {
                        binding.progressBar.isVisible = false
                        Timber.d(uiState.message)
                    }
                    else -> Unit
                }
            }
        }
    }
}
