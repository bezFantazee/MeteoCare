package com.practicum.mateocare.ui.forecast

import com.practicum.mateocare.forecast.domain.models.WeatherForecast
import com.practicum.mateocare.ui.forecast.models.WeatherParameterUiModel

sealed interface WeatherForecastState {
    val latitude: Double?
    val longitude: Double?
    data class Loading(
        override val latitude: Double? = null,
        override val longitude: Double? = null
    ): WeatherForecastState

    data class Content(
        val forecasts: List<WeatherParameterUiModel>,
        val fullForecasts: WeatherForecast,
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