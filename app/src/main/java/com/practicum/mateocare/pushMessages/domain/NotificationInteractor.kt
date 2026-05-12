package com.practicum.mateocare.pushMessages.domain

import com.practicum.mateocare.pushMessages.domain.model.NotificationSettings

interface NotificationInteractor {

    suspend fun saveSettings(settings: NotificationSettings)

    suspend fun getSettings(): NotificationSettings

    suspend fun enableNotifications(hour: Int, minute: Int)

    suspend fun disableNotifications()
}