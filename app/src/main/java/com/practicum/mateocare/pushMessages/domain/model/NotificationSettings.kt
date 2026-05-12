package com.practicum.mateocare.pushMessages.domain.model

data class NotificationSettings(
    val enabled: Boolean = true,
    val hour: Int = 9,
    val minute: Int = 0
)
