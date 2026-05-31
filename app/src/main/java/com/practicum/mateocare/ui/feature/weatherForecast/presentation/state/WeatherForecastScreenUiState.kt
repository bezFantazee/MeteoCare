package com.practicum.mateocare.ui.feature.weatherForecast.presentation.state

import com.practicum.mateocare.forecast.domain.models.WeatherForecast

data class WeatherForecastScreenUiState(
    val weatherState: WeatherForecastState = WeatherForecastState.Loading(null, null),

    val dailyAdviceStatus: DailyAdviceState = DailyAdviceState.Unauthorized,

    val weatherForecast: WeatherForecast? = null
)