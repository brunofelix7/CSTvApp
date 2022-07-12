package me.brunofelix.cstvapp.ui.matchdetails

import android.annotation.SuppressLint
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.preference.PreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import me.brunofelix.cstvapp.R
import me.brunofelix.cstvapp.databinding.ActivityMatchDetailsBinding
import me.brunofelix.cstvapp.extensions.scheduleWorker
import me.brunofelix.cstvapp.extensions.toast
import me.brunofelix.cstvapp.extra.MatchExtra
import me.brunofelix.cstvapp.ui.BaseActivity
import me.brunofelix.cstvapp.util.PATTERN_EEE_HH_MM
import me.brunofelix.cstvapp.util.convertDate
import me.brunofelix.cstvapp.util.loadImage

@AndroidEntryPoint
class MatchDetailsActivity : BaseActivity<ActivityMatchDetailsBinding>(
    ActivityMatchDetailsBinding::inflate
) {

    private val viewModel: MatchDetailsViewModel by viewModels()
    private var uiStateJob: Job? = null
    private var match: MatchExtra? = null

    @SuppressLint("SetTextI18n")
    override fun uiSetup() {
        match = intent.extras?.getParcelable<MatchExtra>(getString(R.string.match_key))

        if (match != null) {
            viewModel.getTeam(match?.teamOneId ?: 0, match?.teamTwoId ?: 0)

            binding.textLeague.text = match?.leagueName
            binding.textSerie.text = match?.serieName
            binding.textTeamOne.text = match?.teamOneName
            binding.textTeamTwo.text = match?.teamTwoName

            if (match?.status == "running") {
                binding.textDateTime.setTextColor(getColor(R.color.red))
                binding.textDateTime.text =
                    "Live\n" + convertDate(match?.scheduledAt, PATTERN_EEE_HH_MM)
            } else {
                binding.textDateTime.text = convertDate(match?.scheduledAt, PATTERN_EEE_HH_MM)
            }

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
                    if (match != null) {
                        val prefs = PreferenceManager.getDefaultSharedPreferences(this)

                        if (prefs.getBoolean(match?.id?.toString(), false)) {
                            toast(getString(R.string.msg_already_scheduled))
                        } else {
                            prefs.edit().apply {
                                putBoolean(match?.id?.toString(), true)
                                apply()
                            }
                            scheduleWorker(matchExtra = match!!, workerTag = match?.id!!)
                            toast(getString(R.string.msg_successfully_scheduled))
                        }
                    }
                    true
                }
                else -> false
            }
        }

        if (match != null) {
            if (match?.status != null && match?.status != "not_started") {
                val menu = binding.toolbar.menu
                menu.findItem(R.id.action_notify).isVisible = false
            }
        }
    }

    override fun collectData() {
        uiStateJob = lifecycleScope.launch {
            viewModel.uiSateFlow.collect { uiState ->
                when (uiState) {
                    is MatchDetailsUIState.Loading -> {
                        binding.layoutError.root.isVisible = false
                        binding.playersOneList.isVisible = false
                        binding.playersTwoList.isVisible = false
                        binding.progressBar.isVisible = true
                    }
                    is MatchDetailsUIState.OnSuccess -> {
                        binding.layoutError.root.isVisible = false
                        binding.playersOneList.isVisible = true
                        binding.playersTwoList.isVisible = true
                        binding.progressBar.isVisible = false

                        val adapterPLayersOne =
                            MatchDetailsPlayersOneAdapter(uiState.data[0]?.players!!)
                        val adapterPLayersTwo =
                            MatchDetailsPlayersTwoAdapter(uiState.data[1]?.players!!)
                        binding.playersOneList.adapter = adapterPLayersOne
                        binding.playersTwoList.adapter = adapterPLayersTwo
                    }
                    is MatchDetailsUIState.OnError -> {
                        binding.layoutError.root.isVisible = true
                        binding.layoutError.textError.text = uiState.message
                        binding.playersOneList.isVisible = false
                        binding.playersTwoList.isVisible = false
                        binding.progressBar.isVisible = false
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
