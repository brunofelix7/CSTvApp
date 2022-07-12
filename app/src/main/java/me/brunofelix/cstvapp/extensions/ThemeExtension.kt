package me.brunofelix.cstvapp.extensions

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import me.brunofelix.cstvapp.R

fun Context.changeAppTheme() {
    val prefs = PreferenceManager.getDefaultSharedPreferences(this)
    val key = getString(R.string.theme_key)

    when (prefs.getBoolean(key, true)) {
        true -> {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        false -> {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}
