package com.practicum.mateocare.dailyAdvice

import com.practicum.mateocare.dailyAdvice.model.GenerateAdviceResult
import com.practicum.mateocare.forecast.domain.models.WeatherForecast
import com.practicum.mateocare.profile.domain.models.UserDailyStatus
import kotlinx.coroutines.flow.Flow

interface DailyAdviceInteractor {

    suspend fun getTodayStatus(
        userId: String
    ): UserDailyStatus?

    suspend fun generateDailyAdvice(
        userId: String,
        forecast: WeatherForecast,
        answers: String
    ): GenerateAdviceResult
}