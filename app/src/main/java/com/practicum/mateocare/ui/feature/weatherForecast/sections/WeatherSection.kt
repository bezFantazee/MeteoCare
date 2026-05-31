package com.practicum.mateocare.ui.feature.weatherForecast.sections

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import com.practicum.mateocare.ui.feature.components.state.ErrorMessage
import com.practicum.mateocare.ui.feature.weatherForecast.presentation.state.WeatherForecastState
import com.practicum.mateocare.ui.feature.weatherForecast.components.weatherGrid.WeatherGridPlaceholder
import com.practicum.mateocare.ui.feature.weatherForecast.components.weatherGrid.WeatherGrid

fun LazyListScope.WeatherSection(
    weatherState: WeatherForecastState,
    expandedItemId: Int?,
    onItemClick: (Int) -> Unit,
    onCollapse: () -> Unit
) {
    when(weatherState) {
        is WeatherForecastState.Loading -> {
            items(3) {
                //LoadingScreen()
                WeatherGridPlaceholder()
            }
        }
        is WeatherForecastState.Error -> {
            item{
                ErrorMessage(weatherState.errorMessage)
            }
        }
        is WeatherForecastState.Empty -> {
            item {
                ErrorMessage(weatherState.message)
            }
        }
        is WeatherForecastState.Content -> {
            val itemPairs = weatherState.forecasts.chunked(2)

            items(
                items = itemPairs,
                key = { pair -> pair.joinToString(separator = "-") { it.id.toString() } }
            ) { pair ->
                WeatherGrid(
                    pair = pair,
                    expandedItemId = expandedItemId,
                    onItemClick = onItemClick,
                    onCollapse = onCollapse
                )
            }
        }
    }
}