package com.practicum.mateocare.ui.feature.weatherForecast.presentation.state

import com.practicum.mateocare.ui.feature.weatherForecast.presentation.models.WeatherParameterUiModel

sealed interface WeatherForecastState {
    val latitude: Double?
    val longitude: Double?
    data class Loading(
        override val latitude: Double? = null,
        override val longitude: Double? = null
    ): WeatherForecastState

    data class Content(
        val forecasts: List<WeatherParameterUiModel>,
        override val latitude: Double?,
        override val longitude: Double?
    ) : WeatherForecastState

    data class Empty(
        val message: String,
        override val latitude: Double? = null,
        override val longitude: Double? = null
    ) : WeatherForecastState

    data class Error(
        val errorMessage: String,
        override val latitude: Double? = null,
        override val longitude: Double? = null
    ) : WeatherForecastState
}