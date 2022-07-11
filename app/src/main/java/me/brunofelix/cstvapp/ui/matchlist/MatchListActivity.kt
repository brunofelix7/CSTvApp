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
import me.brunofelix.cstvapp.ui.BaseActivity
import timber.log.Timber

@AndroidEntryPoint
class MatchListActivity : BaseActivity<ActivityMatchListBinding>(
    ActivityMatchListBinding::inflate), MatchListClickListener {

    private val viewModel: MatchListViewModel by viewModels()
    private var uiStateJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchMatches()
    }

    override fun onDestroy() {
        uiStateJob?.cancel()
        super.onDestroy()
    }

    override fun uiSetup() {
        binding.toolbar.inflateMenu(R.menu.matches_menu)
        binding.toolbar.setOnMenuItemClickListener { item ->
            when(item.itemId) {
                R.id.action_refresh -> {
                    viewModel.fetchMatches()
                    true
                }
                R.id.action_daynight -> {
                    toast("Call Bottom Sheets")
                    true
                }
                else -> false
            }
        }
    }

    override fun collectData() {
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
