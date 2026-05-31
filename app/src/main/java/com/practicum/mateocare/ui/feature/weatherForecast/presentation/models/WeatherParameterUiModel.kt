package com.practicum.mateocare.ui.feature.weatherForecast.presentation.models

data class WeatherParameterUiModel(
    val id: Int,
    val name: String,
    val value: String,
    val unit: String,
    val iconRes: Int? = null,
    val details: List<ForecastDetailsUi>
)
