package me.brunofelix.cstvapp.ui.matchlist

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Switch
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.preference.PreferenceManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import me.brunofelix.cstvapp.R
import me.brunofelix.cstvapp.data.api.response.MatchResponse
import me.brunofelix.cstvapp.databinding.ActivityMatchListBinding
import me.brunofelix.cstvapp.extensions.changeAppTheme
import me.brunofelix.cstvapp.extensions.toast
import me.brunofelix.cstvapp.extra.MatchExtra
import me.brunofelix.cstvapp.ui.BaseActivity
import me.brunofelix.cstvapp.ui.matchdetails.MatchDetailsActivity
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

    override fun uiSetup() {
        binding.toolbar.inflateMenu(R.menu.matches_menu)
        binding.toolbar.setOnMenuItemClickListener { item ->
            when(item.itemId) {
                R.id.action_refresh -> {
                    viewModel.fetchMatches()
                    true
                }
                R.id.action_daynight -> {
                    showSheetsDialog()
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
                        binding.layoutError.root.isVisible = false
                        binding.matchList.isVisible = false
                        binding.progressBar.isVisible = true
                    }
                    is MatchListUIState.OnSuccess -> {
                        binding.layoutError.root.isVisible = false
                        binding.matchList.isVisible = true
                        binding.progressBar.isVisible = false

                        val adapter = MatchListAdapter(uiState.data, this@MatchListActivity)
                        binding.matchList.adapter = adapter
                    }
                    is MatchListUIState.OnError -> {
                        binding.layoutError.root.isVisible = true
                        binding.layoutError.textError.text = uiState.message
                        binding.matchList.isVisible = false
                        binding.progressBar.isVisible = false
                    }
                    else -> Unit
                }
            }
        }
    }

    override fun onItemClick(match: MatchResponse) {
        var matchExtra: MatchExtra? = null

        if (match.opponents != null && match.opponents.isNotEmpty() && match.opponents.size > 1) {
            matchExtra = MatchExtra(
                id = match.id,
                leagueName = match.league?.name,
                serieName = match.serie?.name,
                scheduledAt = match.scheduledAt,
                teamOneId = match.opponents[0].opponent?.id,
                teamTwoId = match.opponents[1].opponent?.id,
                teamOneName = match.opponents[0].opponent?.name,
                teamTwoName = match.opponents[1].opponent?.name,
                teamOneImageUrl = match.opponents[0].opponent?.imageUrl,
                teamTwoImageUrl = match.opponents[1].opponent?.imageUrl
            )
        }

        val intent = Intent(this, MatchDetailsActivity::class.java)
        intent.putExtra(getString(R.string.match_key), matchExtra)
        startActivity(intent)
    }

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private fun showSheetsDialog() {
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(R.layout.sheets_dialog)

        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val switchTheme = dialog.findViewById<Switch>(R.id.switch_theme)
        val themeKey = getString(R.string.theme_key)

        switchTheme?.isChecked = prefs.getBoolean(themeKey, true)

        switchTheme?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                prefs.edit().apply {
                    putBoolean(themeKey, true)
                    apply()
                }
            } else {
                prefs.edit().apply {
                    putBoolean(themeKey, false)
                    apply()
                }
            }
            dialog.dismiss()
            changeAppTheme()
            recreate()
        }
        dialog.setCancelable(true)
        dialog.show()
    }

    override fun onDestroy() {
        uiStateJob?.cancel()
        super.onDestroy()
    }
}
