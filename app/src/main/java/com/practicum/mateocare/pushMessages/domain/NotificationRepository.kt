package com.practicum.mateocare.pushMessages.domain

import com.practicum.mateocare.pushMessages.domain.model.NotificationSettings

interface NotificationRepository {

    suspend fun saveSettings(settings: NotificationSettings)

    suspend fun getSettings(): NotificationSettings

    suspend fun scheduleDailyNotification(settings: NotificationSettings)

    suspend fun cancelDailyNotification()
}