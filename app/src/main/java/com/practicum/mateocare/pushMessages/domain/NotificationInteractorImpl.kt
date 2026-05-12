package com.practicum.mateocare.pushMessages.domain

import com.practicum.mateocare.pushMessages.domain.model.NotificationSettings

class NotificationInteractorImpl(
    private val repository: NotificationRepository
) : NotificationInteractor {

    override suspend fun saveSettings(settings: NotificationSettings) {
        repository.saveSettings(settings)
    }

    override suspend fun getSettings(): NotificationSettings {
        return repository.getSettings()
    }

    override suspend fun enableNotifications(hour: Int, minute: Int) {

        val settings = NotificationSettings(
            enabled = true,
            hour = hour,
            minute = minute
        )

        repository.saveSettings(settings)

        repository.scheduleDailyNotification(settings)
    }

    override suspend fun disableNotifications() {

        repository.cancelDailyNotification()

        repository.saveSettings(
            NotificationSettings(enabled = false)
        )
    }
}