package com.practicum.mateocare.forecast.domain.models

data class WeatherForecast(
    val time: List<String>,
    val temperature: List<Double>,
    val apparentTemperature: List<Double>,
    val relativeHumidity: List<Int>,
    val dewPoint: List<Double>,
    val pressure: List<Double>,
    val windSpeed: List<Double>,
    val precipitation: List<Double>,
    val weatherCode: List<Int>,
)
