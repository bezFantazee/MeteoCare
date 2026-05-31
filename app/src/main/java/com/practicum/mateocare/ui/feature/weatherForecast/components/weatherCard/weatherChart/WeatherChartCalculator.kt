package com.practicum.mateocare.ui.feature.weatherForecast.components.weatherCard.weatherChart

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.abs
import kotlin.math.max

object WeatherChartCalculator {

    fun calculatePadding(
        min: Double,
        max: Double
    ): Double {
        val range = max - min

        return if (range == 0.0) {
            max(abs(min) * 0.1, 1.0)
        } else {
            range * 0.2
        }
    }

    fun calculateWidth(
        itemCount: Int,
        itemWidth: Int = 56,
        spacing: Int = 24
    ): Dp {
        val total = itemCount * itemWidth +
                ((itemCount -1).coerceAtLeast(0) * spacing)

        return total.dp
    }
}