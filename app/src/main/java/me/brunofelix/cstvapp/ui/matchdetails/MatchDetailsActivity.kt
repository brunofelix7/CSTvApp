package me.brunofelix.cstvapp.ui.matchdetails

import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import me.brunofelix.cstvapp.R
import me.brunofelix.cstvapp.databinding.ActivityMatchDetailsBinding
import me.brunofelix.cstvapp.extra.MatchExtra
import me.brunofelix.cstvapp.ui.BaseActivity
import me.brunofelix.cstvapp.util.convertDate
import me.brunofelix.cstvapp.util.loadImage

@AndroidEntryPoint
class MatchDetailsActivity : BaseActivity<ActivityMatchDetailsBinding>(
    ActivityMatchDetailsBinding::inflate) {

    override fun uiSetup() {
        val match = intent.extras?.getParcelable<MatchExtra>(getString(R.string.match_key))

        if (match != null) {
            binding.textLeague.text = match.leagueName
            binding.textSerie.text = match.serieName
            binding.textTeamOne.text = match.teamOneName
            binding.textTeamTwo.text = match.teamTwoName
            binding.textDateTime.text = convertDate(match.scheduledAt)

            if (match.teamOneImageUrl != null) {
                loadImage(binding.imgTeamOne, match.teamOneImageUrl)
            }
            if (match.teamTwoImageUrl != null) {
                loadImage(binding.imgTeamTwo, match.teamTwoImageUrl)
            }
        }

        binding.toolbar.inflateMenu(R.menu.details_menu)
        binding.toolbar.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun collectData() {

    }
}
