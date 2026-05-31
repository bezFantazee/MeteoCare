package com.practicum.mateocare.ui.feature.weatherForecast.components.weatherCard

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.practicum.mateocare.ui.feature.weatherForecast.presentation.models.ForecastDetailsUi
import com.practicum.mateocare.ui.feature.weatherForecast.components.weatherCard.weatherChart.WeatherChart
import com.practicum.mateocare.ui.feature.weatherForecast.presentation.models.WeatherParameterUiModel
import com.practicum.mateocare.ui.theme.MeteoCareTheme

@Composable
fun WeatherCardContent(
    item: WeatherParameterUiModel,
    detailItems: List<ForecastDetailsUi>?
) {
    if(detailItems.isNullOrEmpty()){
        WeatherCardHeader(item.name)
        WeatherCardValue(item.value + " " + item.unit)
    } else {
        WeatherCardHeaderInline(
            name = item.name,
            value = item.value,
            unit = item.unit
        )
        val detailScrollState = rememberScrollState()

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .horizontalScroll(detailScrollState)
                .padding(top = MeteoCareTheme.spacing.md)
        )
        {
            WeatherDetailTable(
                detailItems = detailItems
            )

            WeatherChart(
                data = detailItems,
                pointSpacing = WeatherCardDefaults.pointSpacing
            )
        }
    }
}

@Composable
fun WeatherCardValue(
    value: String
) {
    Text(
        text = value,
        style = MaterialTheme.typography.displaySmall,
        color = MaterialTheme.colorScheme.surface
    )
}