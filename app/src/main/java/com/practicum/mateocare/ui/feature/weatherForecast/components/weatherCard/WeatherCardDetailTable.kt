package com.practicum.mateocare.ui.feature.weatherForecast.components.weatherCard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.practicum.mateocare.ui.feature.weatherForecast.presentation.models.ForecastDetailsUi

@Composable
fun WeatherDetailTable(
    detailItems: List<ForecastDetailsUi>
) {
    LazyRow {
        items(detailItems) { item ->
            Column(
                modifier = Modifier.height(WeatherCardDefaults.textItemSize)
            ) {
                Text(
                    text = item.time,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.width(WeatherCardDefaults.textItemSize)
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = item.value,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.surface,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.width(WeatherCardDefaults.textItemSize)
                )
            }
        }
    }
}