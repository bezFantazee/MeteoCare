package com.practicum.mateocare.ui.feature.weatherForecast.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.practicum.mateocare.forecast.domain.models.WeatherForecast
import com.practicum.mateocare.ui.feature.weatherForecast.presentation.state.WeatherForecastScreenUiState
import com.practicum.mateocare.ui.feature.weatherForecast.sections.DailyAdviceSection
import com.practicum.mateocare.ui.feature.weatherForecast.sections.WeatherSection
import com.practicum.mateocare.ui.theme.MeteoCareTheme

@Composable
fun WeatherForecastScreen(
    state: WeatherForecastScreenUiState,
    getDailyAdviceAndStatus: (WeatherForecast?, String?) -> Unit,
    modifier: Modifier = Modifier
) {
    //анимация раскрытия жлемента
    val expandedItemId = remember { mutableStateOf<Int?>(null) }

    LazyColumn(
        contentPadding = PaddingValues(MeteoCareTheme.spacing.md),
        verticalArrangement = Arrangement.spacedBy(MeteoCareTheme.spacing.md),
        modifier = modifier
            .fillMaxSize()
            .clip(shape = RoundedCornerShape(
                topStart = MeteoCareTheme.spacing.xl,
                topEnd = MeteoCareTheme.spacing.xl,
                bottomStart = 0.dp,
                bottomEnd = 0.dp
            ))
            .background(MaterialTheme.colorScheme.surface)
    ) {
        WeatherSection(
            weatherState = state.weatherState,
            expandedItemId = expandedItemId.value,
            onItemClick = { itemId ->
                expandedItemId.value =
                    if (expandedItemId.value == itemId) null else itemId
            },
            onCollapse = {
                expandedItemId.value = null
            }
        )

        item {
            DailyAdviceSection(
                fullForecast = state.weatherForecast,
                adviceState = state.dailyAdviceStatus,
                getDailyAdviceAndStatus = getDailyAdviceAndStatus,
            )
        }
    }
}