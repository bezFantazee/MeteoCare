package com.practicum.mateocare.pushMessages.data

import android.Manifest
import android.content.Context
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class DailyNotificationWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override suspend fun doWork(): Result {

        return try {

            NotificationHelper.showNotification(
                context = applicationContext,
                title = "MateoCare",
                message = "Пройдите ежедневный опрос"
            )
            Log.d("NOTIFICATION12", "Worker started")

            Result.success()

        } catch (e: Exception) {
            Log.d("NOTIFICATION12", e.message.toString())
            Result.failure()
        }
    }
}