package me.brunofelix.cstvapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import me.brunofelix.cstvapp.R

abstract class BaseActivity<B : ViewBinding>(val bindingFactory: (LayoutInflater) -> B) : AppCompatActivity() {

    lateinit var binding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTheme(R.style.ThemeThemeCSTvApp)

        binding = bindingFactory(layoutInflater)
        setContentView(binding.root)

        uiSetup()
        collectData()
    }

    abstract fun uiSetup()
    abstract fun collectData()
}
