package com.practicum.mateocare.ai.prompts.model

import com.practicum.mateocare.ai.data.weather.model.WeatherFeatures
import com.practicum.mateocare.forecast.domain.models.WeatherForecast
import com.practicum.mateocare.profile.domain.models.MeteoStatus

data class AdviceInput(
    val age: Int,
    val weather: WeatherFeatures,
    val meteoStatus: MeteoStatus,
    val answers: String
)
