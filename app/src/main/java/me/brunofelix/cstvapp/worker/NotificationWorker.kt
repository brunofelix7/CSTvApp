package me.brunofelix.cstvapp.worker

import android.content.Context
import androidx.preference.PreferenceManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import me.brunofelix.cstvapp.R
import me.brunofelix.cstvapp.extensions.sendNotification
import timber.log.Timber

class NotificationWorker (
    private val context: Context,
    private val parameters: WorkerParameters
) : Worker(context, parameters) {

    override fun doWork(): Result {
        return try {
            val prefs = PreferenceManager.getDefaultSharedPreferences(applicationContext)
            val workerTag = inputData.getLong(context.getString(R.string.worker_tag_key), 0)
            val teamsName = inputData.getString(context.getString(R.string.teams_name_key))
            val leagueName = inputData.getString(context.getString(R.string.league_name_key))

            applicationContext.sendNotification(workerTag, teamsName!!, leagueName!!)

            prefs.edit().apply {
                remove(workerTag.toString())
                apply()
            }

            Result.success()
        } catch (t: Throwable) {
            Timber.e(t)
            Result.failure()
        }
    }
}
