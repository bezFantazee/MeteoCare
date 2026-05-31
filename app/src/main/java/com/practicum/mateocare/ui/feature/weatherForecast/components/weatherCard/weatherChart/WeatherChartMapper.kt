package com.practicum.mateocare.ui.feature.weatherForecast.components.weatherCard.weatherChart

import com.practicum.mateocare.ui.components.chart.ChartModel
import com.practicum.mateocare.ui.feature.weatherForecast.presentation.models.ForecastDetailsUi

object WeatherChartMapper {
    fun map(data: List<ForecastDetailsUi>): ChartModel? {
        val values = data.mapNotNull {
            it.value.toFloatOrNull()
        }

        if(values.isEmpty()) return null

        val min = values.minOrNull()?.toDouble() ?: return null
        val max = values.maxOrNull()?.toDouble() ?: return null

        val padding = WeatherChartCalculator.calculatePadding(
            min,
            max
        )

        return ChartModel(
            values = values,
            minY = min - padding,
            maxY = max + padding
        )
    }
}