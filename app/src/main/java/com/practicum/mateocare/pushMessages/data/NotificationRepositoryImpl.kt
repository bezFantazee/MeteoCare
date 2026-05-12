package com.practicum.mateocare.pushMessages.data

import android.content.Context
import android.util.Log
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.practicum.mateocare.pushMessages.domain.NotificationRepository
import com.practicum.mateocare.pushMessages.domain.model.NotificationSettings
import java.util.Calendar
import java.util.concurrent.TimeUnit

class NotificationRepositoryImpl(
    private val context: Context
) : NotificationRepository {

    private val prefs =
        context.getSharedPreferences("notification_prefs", Context.MODE_PRIVATE)

    override suspend fun saveSettings(settings: NotificationSettings) {

        prefs.edit()
            .putBoolean("enabled", settings.enabled)
            .putInt("hour", settings.hour)
            .putInt("minute", settings.minute)
            .apply()
    }

    override suspend fun getSettings(): NotificationSettings {

        return NotificationSettings(
            enabled = prefs.getBoolean("enabled", false),
            hour = prefs.getInt("hour", 9),
            minute = prefs.getInt("minute", 0)
        )
    }

    override suspend fun scheduleDailyNotification(
        settings: NotificationSettings
    ) {

        val now = Calendar.getInstance()

        val target = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, settings.hour)
            set(Calendar.MINUTE, settings.minute)
            set(Calendar.SECOND, 0)
        }

        if (target.before(now)) {
            target.add(Calendar.DAY_OF_MONTH, 1)
        }

        val initialDelay =
            target.timeInMillis - now.timeInMillis

        val workRequest =
            PeriodicWorkRequestBuilder<DailyNotificationWorker>(
                24,
                TimeUnit.HOURS
            )
                .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
                .build()

        WorkManager.getInstance(context)
            .enqueueUniquePeriodicWork(
                "daily_notification",
                ExistingPeriodicWorkPolicy.UPDATE,
                workRequest
            )
        Log.d("NOTIFICATION12", "Work scheduled")
    }

    override suspend fun cancelDailyNotification() {

        WorkManager.getInstance(context)
            .cancelUniqueWork("daily_notification")
    }
}