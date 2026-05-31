package com.practicum.mateocare.ui.feature.profile.presentation.models

import com.practicum.mateocare.profile.domain.models.MeteoStatus
import com.practicum.mateocare.profile.domain.models.UserDailyStatus

data class MeteoStatusUi (
    val meteoStatus: MeteoStatus,
    val advice: String,
    val healthHistory: List<UserDailyStatus>?
)