package com.practicum.mateocare.ui.feature.weatherForecast.components.weatherCard

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.practicum.mateocare.ui.components.cards.PrimaryCard
import com.practicum.mateocare.ui.feature.weatherForecast.presentation.models.ForecastDetailsUi
import com.practicum.mateocare.ui.feature.weatherForecast.presentation.models.WeatherParameterUiModel

@Composable
fun WeatherCard(
    item: WeatherParameterUiModel,
    modifier: Modifier = Modifier,
    detailItems: List<ForecastDetailsUi>? = null
) {
    PrimaryCard(
       modifier = modifier,
        content = {
            WeatherCardContent(
                item,
                detailItems
            )
        }
    )
}
