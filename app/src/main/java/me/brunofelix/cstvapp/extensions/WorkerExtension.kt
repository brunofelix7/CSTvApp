package me.brunofelix.cstvapp.extensions

import android.content.Context
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import me.brunofelix.cstvapp.R
import me.brunofelix.cstvapp.extra.MatchExtra
import me.brunofelix.cstvapp.util.*
import me.brunofelix.cstvapp.worker.NotificationWorker
import java.util.concurrent.TimeUnit

fun Context.scheduleWorker(matchExtra: MatchExtra, workerTag: Long) {
    val inputData = Data.Builder().apply {
        putLong(getString(R.string.worker_tag_key), workerTag)
        putString(getString(R.string.teams_name_key), matchExtra.teamOneName + " vs " + matchExtra.teamTwoName)
        putString(getString(R.string.league_name_key), matchExtra.leagueName)
    }

    val dateConverted = convertDate(matchExtra.scheduledAt, PATTERN_DD_MM_YYYY_HH_MM)
    val timestamp = convertToTimestamp(dateConverted, PATTERN_DD_MM_YYYY_HH_MM)
    val currentTimestampFormatted = convertFromTimestamp(System.currentTimeMillis(), PATTERN_DD_MM_YYYY_HH_MM, false)
    val currentTimestamp = convertToTimestamp(currentTimestampFormatted, PATTERN_DD_MM_YYYY_HH_MM)
    val duration = getDuration(timestamp, currentTimestamp)

    val notificationWork = OneTimeWorkRequest.Builder(NotificationWorker::class.java)
        .setInitialDelay(duration, TimeUnit.MINUTES)
        .setInputData(inputData.build())
        .addTag(workerTag.toString())
        .build()
    WorkManager.getInstance(this).enqueue(notificationWork)
}

fun Context.cancelWorker(workerTag: Long) {
    WorkManager.getInstance(this).cancelAllWorkByTag(workerTag.toString())
}
