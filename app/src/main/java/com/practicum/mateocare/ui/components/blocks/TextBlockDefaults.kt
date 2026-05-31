package com.practicum.mateocare.ui.components.blocks

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.practicum.mateocare.ui.theme.MeteoCareTheme

object AppTextBlockDefaults {
    @Composable
    fun primaryColors() = TextBlockColors(
        textColor = MaterialTheme.colorScheme.onTertiaryContainer,
        backgroundColor = MaterialTheme.colorScheme.tertiaryContainer
    )

    @Composable
    fun secondaryColors() = TextBlockColors(
        textColor = MaterialTheme.colorScheme.onSecondaryContainer,
        backgroundColor = MaterialTheme.colorScheme.secondaryContainer
    )

    @Composable
    fun errorColors() = TextBlockColors(
        textColor = MaterialTheme.colorScheme.onErrorContainer,
        backgroundColor = MaterialTheme.colorScheme.errorContainer
    )

    val Shape
        @Composable
        get() = MaterialTheme.shapes.medium

    val ContentPadding
        @Composable
        get() = PaddingValues(
            MeteoCareTheme.spacing.lg,
            MeteoCareTheme.spacing.md
        )

    val TextStyle
        @Composable
        get() = MaterialTheme.typography.bodyLarge
}