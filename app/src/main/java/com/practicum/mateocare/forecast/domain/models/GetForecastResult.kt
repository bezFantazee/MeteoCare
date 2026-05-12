package com.practicum.mateocare.forecast.domain.models

sealed class GetForecastResult {
    data class Success(
        val forecasts: WeatherForecast,
        val forecastUnits: WeatherForecastUnits
    ) : GetForecastResult()
    object NetworkError: GetForecastResult()
    object NoResults: GetForecastResult()
    object ServerError: GetForecastResult()
}