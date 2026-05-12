package com.practicum.mateocare.ai.data.weather.processor

import com.practicum.mateocare.ai.data.weather.model.WeatherFeatures
import com.practicum.mateocare.forecast.domain.models.WeatherForecast

class WeatherFeatureExtractor {
    fun extract(raw: WeatherForecast): WeatherFeatures {
        val tempAvg = raw.temperature.average()
        val tempMin = raw.temperature.min()
        val tempMax = raw.temperature.max()
        val tempDelta = tempMax - tempMin

        val feelsLikeDiff = raw.apparentTemperature.zip(raw.temperature)
            .map { it.first - it.second }
            .average()

        val humidityAvg = raw.relativeHumidity.average().toInt()
        val highHumidityHours = raw.relativeHumidity.count { it > 80 }

        val pressureAvg = raw.pressure.average()
        val pressureDrop = raw.pressure.max() - raw.pressure.min()

        val windAvg = raw.windSpeed.average()

        val precipitationTotal = raw.precipitation.sum()

        return WeatherFeatures(
            tempAvg = tempAvg,
            tempMin = tempMin,
            tempMax = tempMax,
            tempDelta = tempDelta,
            feelsLikeDiff = feelsLikeDiff,
            humidityAvg = humidityAvg,
            highHumidityHours = highHumidityHours,
            pressureAvg = pressureAvg,
            pressureDrop = pressureDrop,
            windAvg = windAvg,
            precipitationTotal = precipitationTotal
        )
    }
}