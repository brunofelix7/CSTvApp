package me.brunofelix.cstvapp.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import me.brunofelix.cstvapp.BuildConfig
import timber.log.Timber

@BindingAdapter("image")
fun loadImage(view: ImageView, url: String) {
    Glide.with(view)
        .load(url)
        .into(view)
}

fun initDebugLog() {
    if (BuildConfig.DEBUG) {
        Timber.plant(Timber.DebugTree())
    }
}
