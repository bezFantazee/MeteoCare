package com.practicum.mateocare.ui.feature.weatherForecast.components.weatherGrid

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.practicum.mateocare.ui.components.cards.PrimaryCardPlaceholder

@Composable
fun WeatherGridPlaceholder(
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(WeatherGridDefaults.ItemSpacing),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(WeatherGridDefaults.GridSpacing),
            modifier = Modifier.fillMaxWidth()
        ) {
            repeat(2) {
                PrimaryCardPlaceholder(
                    modifier = Modifier.weight(1f)
                        .aspectRatio(WeatherGridDefaults.CardAspectRatio)
                )
            }
        }
    }
}