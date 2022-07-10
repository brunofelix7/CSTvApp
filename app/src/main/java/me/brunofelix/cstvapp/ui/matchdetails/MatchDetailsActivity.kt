package me.brunofelix.cstvapp.ui.matchdetails

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import me.brunofelix.cstvapp.R
import me.brunofelix.cstvapp.databinding.ActivityMatchDetailsBinding

@AndroidEntryPoint
class MatchDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMatchDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTheme(R.style.ThemeThemeCSTvApp)

        binding = ActivityMatchDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
