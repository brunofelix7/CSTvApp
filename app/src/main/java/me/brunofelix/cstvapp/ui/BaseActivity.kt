package me.brunofelix.cstvapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.viewbinding.ViewBinding
import me.brunofelix.cstvapp.R

abstract class BaseActivity<B : ViewBinding>(val bindingFactory: (LayoutInflater) -> B) : AppCompatActivity() {

    private lateinit var binding: B
    private val duration: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTheme(R.style.Theme_ThemeCSTvApp)

        binding = bindingFactory(layoutInflater)
        setContentView(binding.root)

        installSplashScreen()
    }
}
