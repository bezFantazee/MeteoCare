package com.practicum.mateocare.forecast.domain

import com.practicum.mateocare.forecast.domain.models.GetForecastResult
import kotlinx.coroutines.flow.Flow

interface GetWeatherForecastInteractor {
    fun getWeatherForecast(latitude: Double, longitude: Double): Flow<GetForecastResult>
}