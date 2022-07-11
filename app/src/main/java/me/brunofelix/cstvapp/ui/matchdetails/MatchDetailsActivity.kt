package me.brunofelix.cstvapp.ui.matchdetails

import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import me.brunofelix.cstvapp.R
import me.brunofelix.cstvapp.databinding.ActivityMatchDetailsBinding
import me.brunofelix.cstvapp.extensions.toast
import me.brunofelix.cstvapp.extra.MatchExtra
import me.brunofelix.cstvapp.ui.BaseActivity
import me.brunofelix.cstvapp.ui.matchlist.MatchListAdapter
import me.brunofelix.cstvapp.ui.matchlist.MatchListUIState
import me.brunofelix.cstvapp.util.convertDate
import me.brunofelix.cstvapp.util.loadImage

@AndroidEntryPoint
class MatchDetailsActivity : BaseActivity<ActivityMatchDetailsBinding>(
    ActivityMatchDetailsBinding::inflate
) {

    var match: MatchExtra? = null

    private val viewModel: MatchDetailsViewModel by viewModels()
    private var uiStateJob: Job? = null

    override fun uiSetup() {
        match = intent.extras?.getParcelable<MatchExtra>(getString(R.string.match_key))

        if (match != null) {
            viewModel.getTeam(match?.teamOneId ?: 0, match?.teamTwoId ?: 0)

            binding.textLeague.text = match?.leagueName
            binding.textSerie.text = match?.serieName
            binding.textTeamOne.text = match?.teamOneName
            binding.textTeamTwo.text = match?.teamTwoName
            binding.textDateTime.text = convertDate(match?.scheduledAt)

            if (match?.teamOneImageUrl != null && match?.teamOneImageUrl != null) {
                loadImage(binding.imgTeamOne, match?.teamOneImageUrl!!)
            }
            if (match?.teamTwoImageUrl != null && match?.teamTwoImageUrl != null) {
                loadImage(binding.imgTeamTwo, match?.teamTwoImageUrl!!)
            }
        }

        binding.toolbar.inflateMenu(R.menu.details_menu)
        binding.toolbar.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        binding.toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_notify -> {
                    toast("Schedule worker")
                    true
                }
                else -> false
            }
        }
    }

    override fun collectData() {
        uiStateJob = lifecycleScope.launch {
            viewModel.uiSateFlow.collect { uiState ->
                when (uiState) {
                    is MatchDetailsUIState.Loading -> {
                        binding.playersOneList.isVisible = false
                        binding.playersTwoList.isVisible = false
                        binding.progressBar.isVisible = true
                    }
                    is MatchDetailsUIState.OnSuccess -> {
                        binding.playersOneList.isVisible = true
                        binding.playersTwoList.isVisible = true
                        binding.progressBar.isVisible = false

                        val adapterPLayersOne = MatchDetailsPlayersOneAdapter(uiState.data[0]?.players!!)
                        val adapterPLayersTwo = MatchDetailsPlayersTwoAdapter(uiState.data[1]?.players!!)
                        binding.playersOneList.adapter = adapterPLayersOne
                        binding.playersTwoList.adapter = adapterPLayersTwo
                    }
                    is MatchDetailsUIState.OnError -> {
                        // TODO: Layout empty
                        binding.playersOneList.isVisible = false
                        binding.playersTwoList.isVisible = false
                        binding.progressBar.isVisible = false
                        toast(uiState.message)
                    }
                    else -> Unit
                }
            }
        }
    }

    override fun onDestroy() {
        uiStateJob?.cancel()
        super.onDestroy()
    }
}
