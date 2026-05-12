package com.practicum.mateocare.ai.domain

import com.practicum.mateocare.ai.domain.models.GetAiResult
import com.practicum.mateocare.ai.prompts.model.AdviceInput
import com.practicum.mateocare.ai.prompts.model.StatusInput
import com.practicum.mateocare.forecast.domain.models.WeatherForecast
import com.practicum.mateocare.profile.domain.models.MeteoStatus
import kotlinx.coroutines.flow.Flow

interface AiInteractor {
    suspend fun getAdvice(age: Int, meteoStatus: MeteoStatus, forecast: WeatherForecast, answers: String): Flow<GetAiResult>
    suspend fun getMeteoStatus(age: Int, answers: String): Flow<GetAiResult>
}