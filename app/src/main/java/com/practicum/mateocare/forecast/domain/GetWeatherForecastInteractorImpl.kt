package com.practicum.mateocare.forecast.domain

import com.practicum.mateocare.forecast.domain.models.GetForecastResult
import kotlinx.coroutines.flow.Flow

class GetWeatherForecastInteractorImpl(
    private val repository: GetWeatherForecastRepository
) : GetWeatherForecastInteractor {
    override fun getWeatherForecast(
        latitude: Double,
        longitude: Double
    ): Flow<GetForecastResult> {
        return repository.getWeatherForecast(latitude, longitude)
    }
}