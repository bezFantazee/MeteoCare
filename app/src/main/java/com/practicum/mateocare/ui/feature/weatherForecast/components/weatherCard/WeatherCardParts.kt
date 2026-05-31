package com.practicum.mateocare.ui.feature.weatherForecast.components.weatherCard

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.practicum.mateocare.ui.components.cards.PrimaryCardHeader
import com.practicum.mateocare.ui.theme.MeteoCareTheme

@Composable
fun WeatherCardHeader(
    name: String
) {
    PrimaryCardHeader(name)
}

@Composable
fun WeatherCardHeaderInline(
    name: String,
    value: String,
    unit: String
) {
    Row {
        PrimaryCardHeader(name + ":")
        Spacer(
            Modifier.width(MeteoCareTheme.spacing.xs)
        )
        PrimaryCardHeader(value)
        Spacer(
            Modifier.width(MeteoCareTheme.spacing.xs)
        )
        PrimaryCardHeader(unit)
    }
}
