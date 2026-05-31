package com.practicum.mateocare.ui.feature.components.dailyHealth

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object DailyHealthDefaults {
    @Composable
    fun color(status: DailyHealthStatus): Color {
        return when(status) {
            DailyHealthStatus.High -> {
                MaterialTheme.colorScheme.error
            }
            DailyHealthStatus.Medium -> {
                MaterialTheme.colorScheme.primaryContainer
            }
            DailyHealthStatus.Low -> {
                MaterialTheme.colorScheme.tertiaryContainer
            }
            DailyHealthStatus.Default -> {
                MaterialTheme.colorScheme.outline
            }
        }
    }
}