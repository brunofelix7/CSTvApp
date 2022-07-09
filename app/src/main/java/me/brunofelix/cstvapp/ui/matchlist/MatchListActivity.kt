package me.brunofelix.cstvapp.ui.matchlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import me.brunofelix.cstvapp.databinding.ActivityMatchListBinding

class MatchListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMatchListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUI()
    }

    private fun initUI() {
        binding = ActivityMatchListBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}