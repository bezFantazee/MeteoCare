package com.practicum.mateocare.pushMessages.data

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.practicum.mateocare.R

object NotificationHelper {

    private const val CHANNEL_ID = "mateocare_daily"

    @RequiresApi(Build.VERSION_CODES.O)
    fun createChannel(context: Context) {

        val channel = NotificationChannel(
            CHANNEL_ID,
            "Daily Notifications",
            NotificationManager.IMPORTANCE_DEFAULT
        )

        val manager =
            context.getSystemService(Context.NOTIFICATION_SERVICE)
                    as NotificationManager

        manager.createNotificationChannel(channel)
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun showNotification(
        context: Context,
        title: String,
        message: String
    ) {

        val notification =
            NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()

        Log.d("NOTIFICATION12", "Showing notification")
        NotificationManagerCompat
            .from(context)
            .notify(1, notification)
    }
}