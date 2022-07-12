package me.brunofelix.cstvapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import me.brunofelix.cstvapp.extensions.changeAppTheme
import me.brunofelix.cstvapp.util.initDebugLog

@HiltAndroidApp
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initDebugLog()
        changeAppTheme()
    }
}
