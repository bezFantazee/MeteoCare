package com.practicum.mateocare.ui.components.chart

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.practicum.mateocare.ui.theme.MeteoCareTheme

object AppChartDefaults {
    val PrimaryColor
        @Composable
        get() = MaterialTheme.colorScheme.tertiary

    val PrimaryPointSize = 10.dp

    val PrimaryLineThickness = 2.dp

    val PointSpacing = 70.dp

    val ContentPadding
        @Composable
        get() = PaddingValues(
            horizontal = MeteoCareTheme.spacing.md,
            vertical = MeteoCareTheme.spacing.lg
        )
}