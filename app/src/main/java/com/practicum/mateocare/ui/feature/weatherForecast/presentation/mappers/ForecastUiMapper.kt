package com.practicum.mateocare.ui.feature.weatherForecast.presentation.mappers

import com.practicum.mateocare.forecast.domain.models.WeatherForecast
import com.practicum.mateocare.forecast.domain.models.WeatherForecastUnits
import com.practicum.mateocare.ui.feature.weatherForecast.presentation.models.ForecastDetailsUi
import com.practicum.mateocare.ui.feature.weatherForecast.presentation.models.WeatherParameterUiModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ForecastUiMapper {
    fun map(forecast: WeatherForecast, units: WeatherForecastUnits, hourIndex: Int): List<WeatherParameterUiModel> {
        return listOf(
            WeatherParameterUiModel(
                id = 0,
                name = "Температура",
                value = "${forecast.temperature.getOrNull(hourIndex) ?: "-"}",
                unit = units.temperature ?: "-",
                details = mapDetails(forecast.temperature, forecast.time)
            ),
            WeatherParameterUiModel(
                id = 1,
                name = "Скорость ветра",
                value = "${forecast.windSpeed.getOrNull(hourIndex) ?: "-"}",
                unit = units.windSpeed ?: "-",
                details = mapDetails(forecast.windSpeed, forecast.time)
            ),
            WeatherParameterUiModel(
                id = 2,
                name = "Влажность",
                value = "${forecast.relativeHumidity.getOrNull(hourIndex) ?: "-"}",
                unit = units.relativeHumidity ?: "-",
                details = mapDetails(forecast.relativeHumidity, forecast.time)
            ),
            WeatherParameterUiModel(
                id = 3,
                name = "Точка россы",
                value = "${forecast.dewPoint.getOrNull(hourIndex) ?: "-"}",
                unit = units.dewPoint ?: "-",
                details = mapDetails(forecast.dewPoint, forecast.time)
            ),
            WeatherParameterUiModel(
                id = 4,
                name="Давление",
                value = "${forecast.pressure.getOrNull(hourIndex) ?: "-"}",
                unit = units.pressure ?: "-",
                details = mapDetails(forecast.pressure, forecast.time)
            )
        )
    }

    private fun <T> mapDetails(values: List<T>, times: List<String>): List<ForecastDetailsUi>{
        val indexes = values.indices
            .filter { it %DETAILS_STEP == 0 && it <= MAX_HOURS_INDEX }

        return indexes.map { index ->
            ForecastDetailsUi(
                time = formatTime(times[index]),
                value = values
                    .getOrNull(index)
                    .toString()
                    ?: "-",
            )
        }
    }

    private fun formatTime(time: String): String {
        val inputFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
        val outputFormatter = DateTimeFormatter.ofPattern("HH:mm")

        return LocalDateTime
            .parse(time, inputFormatter)
            .format(outputFormatter)
    }

    private companion object {
        const val DETAILS_STEP = 3
        const val MAX_HOURS_INDEX = 24
    }
}