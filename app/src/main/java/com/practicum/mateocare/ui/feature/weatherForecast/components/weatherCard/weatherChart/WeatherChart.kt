package com.practicum.mateocare.ui.feature.weatherForecast.components.weatherCard.weatherChart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.practicum.mateocare.ui.components.chart.PrimaryChart
import com.practicum.mateocare.ui.feature.components.state.ErrorMessage
import com.practicum.mateocare.ui.feature.weatherForecast.components.weatherCard.WeatherCardDefaults
import com.practicum.mateocare.ui.feature.weatherForecast.presentation.models.ForecastDetailsUi
import com.practicum.mateocare.ui.theme.MeteoCareTheme

@Composable
fun WeatherChart(data: List<ForecastDetailsUi>, pointSpacing: Dp, modifier: Modifier = Modifier){
    val model = remember(data) {
        WeatherChartMapper.map(data)
    }

    val containerWidth = remember(data.size) {
        WeatherChartCalculator.calculateWidth(data.size)
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .width(containerWidth)
            .height(WeatherCardDefaults.chartHeight)
            .padding(MeteoCareTheme.spacing.xs)
            .clip(MaterialTheme.shapes.small)
            .background(MaterialTheme.colorScheme.surface)
    ){
        if(model != null) {
            PrimaryChart(
                model = model,
                pointSpacing = pointSpacing,
                enableScroll = false
            )
        } else {
            ErrorMessage("Не удалось загрузить график")
        }
    }
}