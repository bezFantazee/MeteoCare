package com.practicum.mateocare.ai.domain.models

import com.practicum.mateocare.profile.domain.models.MeteoStatus

data class MeteoAdviceResult(
    val advice: String,
    val meteoStatus: MeteoStatus,
    val dailyStatus: String?
)
