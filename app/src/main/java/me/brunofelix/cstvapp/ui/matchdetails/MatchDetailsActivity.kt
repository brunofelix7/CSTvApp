package me.brunofelix.cstvapp.ui.matchdetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import me.brunofelix.cstvapp.databinding.ActivityMatchDetailsBinding

class MatchDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMatchDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUI()
    }

    private fun initUI() {
        binding = ActivityMatchDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}