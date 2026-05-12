package com.practicum.mateocare.ai.domain.models

import com.practicum.mateocare.forecast.domain.models.GetForecastResult
import com.practicum.mateocare.forecast.domain.models.WeatherForecast
import com.practicum.mateocare.forecast.domain.models.WeatherForecastUnits
import com.practicum.mateocare.profile.domain.models.MeteoStatus

sealed class GetAiResult {
    data class Success(
        val data: MeteoAdviceResult
    ) : GetAiResult()
    object NetworkError: GetAiResult()
    object NoResults: GetAiResult()
    object ServerError: GetAiResult()
}