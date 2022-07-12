package me.brunofelix.cstvapp.extensions

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import me.brunofelix.cstvapp.R
import me.brunofelix.cstvapp.ui.matchdetails.MatchDetailsActivity

fun Context.createNotificationChannel() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channelName = getString(R.string.channel_name)
        val channelDescription = resources.getString(R.string.channel_description)

        val channel = NotificationChannel(
            getString(R.string.channel_id),
            channelName,
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            enableLights(true)
            enableVibration(true)
            lightColor = Color.GREEN
            description = channelDescription
        }
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}

fun Context.sendNotification(notificationId: Long, teamsName: String, leagueName: String) {
    val intent = Intent(this, MatchDetailsActivity::class.java).apply {
        putExtra(getString(R.string.match_key), notificationId)
    }
    val pendingIntent = TaskStackBuilder.create(this).run {
        addNextIntentWithParentStack(intent)
        getPendingIntent(0, FLAG_UPDATE_CURRENT)
    }

    val builder = NotificationCompat.Builder(this, getString(R.string.channel_id))
        .setSmallIcon(R.drawable.ic_logo)
        .setContentTitle(teamsName)
        .setColor(ContextCompat.getColor(this, R.color.red))
        .setContentText(leagueName)
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    with(NotificationManagerCompat.from(this)) {
        notify(notificationId.toInt(), builder.build())
    }
}

fun Context.cancelNotification(notificationId: Int) {
    val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.cancel(notificationId)
}
