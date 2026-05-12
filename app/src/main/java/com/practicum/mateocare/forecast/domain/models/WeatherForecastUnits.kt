package com.practicum.mateocare.forecast.domain.models

data class WeatherForecastUnits(
    val apparentTemperature: String?,
    val dewPoint: String?,
    val precipitation: String?,
    val pressure: String?,
    val relativeHumidity: String?,
    val temperature: String?,
    val time: String?,
    val windSpeed: String?
)
