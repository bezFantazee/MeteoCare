package com.practicum.mateocare.ai.data.weather.model

data class WeatherFeatures(
    val tempAvg: Double,
    val tempMin: Double,
    val tempMax: Double,
    val tempDelta: Double,

    val feelsLikeDiff: Double,

    val humidityAvg: Int,
    val highHumidityHours: Int,

    val pressureAvg: Double,
    val pressureDrop: Double,

    val windAvg: Double,
    val precipitationTotal: Double
)
