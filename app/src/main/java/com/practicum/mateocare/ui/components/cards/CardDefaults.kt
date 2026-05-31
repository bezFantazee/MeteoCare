package com.practicum.mateocare.ui.components.cards

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.practicum.mateocare.ui.theme.MeteoCareTheme

object AppCardDefaults {
    val PrimaryColor
        @Composable
        get() = MaterialTheme.colorScheme.primaryContainer

    val CardShape
        @Composable
        get() = MaterialTheme.shapes.medium

    val ContentShape
        @Composable
        get() = MaterialTheme.shapes.small

    val ContentPadding
        @Composable
        get() = PaddingValues(
            horizontal = MeteoCareTheme.spacing.sm,
            vertical = MeteoCareTheme.spacing.md
        )
}