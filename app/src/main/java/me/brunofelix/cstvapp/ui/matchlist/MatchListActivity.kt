package me.brunofelix.cstvapp.ui.matchlist

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import me.brunofelix.cstvapp.R
import me.brunofelix.cstvapp.data.api.response.MatchResponse
import me.brunofelix.cstvapp.databinding.ActivityMatchListBinding
import me.brunofelix.cstvapp.extensions.toast
import timber.log.Timber

@AndroidEntryPoint
class MatchListActivity : AppCompatActivity(), MatchListClickListener {

    private lateinit var binding: ActivityMatchListBinding
    private val viewModel: MatchListViewModel by viewModels()
    private var uiStateJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTheme(R.style.ThemeThemeCSTvApp)

        binding = ActivityMatchListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.fetchMatches()
        collectData()
    }

    override fun onDestroy() {
        uiStateJob?.cancel()
        super.onDestroy()
    }

    private fun collectData() {
        uiStateJob = lifecycleScope.launch {
            viewModel.uiSateFlow.collect { uiState ->
                when(uiState) {
                    is MatchListUIState.Loading -> {
                        binding.matchList.isVisible = false
                        binding.progressBar.isVisible = true
                    }
                    is MatchListUIState.OnSuccess -> {
                        binding.matchList.isVisible = true
                        binding.progressBar.isVisible = false

                        val adapter = MatchListAdapter(uiState.data, this@MatchListActivity)
                        binding.matchList.adapter = adapter
                    }
                    is MatchListUIState.OnError -> {
                        // TODO: Layout empty
                        binding.matchList.isVisible = false
                        binding.progressBar.isVisible = false
                        toast(uiState.message)
                    }
                    else -> Unit
                }
            }
        }
    }

    override fun onItemClick(match: MatchResponse) {
        Timber.d(match.toString())
    }
}
