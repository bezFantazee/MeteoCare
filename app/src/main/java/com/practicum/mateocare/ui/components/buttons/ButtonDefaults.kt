package com.practicum.mateocare.ui.components.buttons

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.remote.creation.modifiers.Shape
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import com.practicum.mateocare.ui.theme.MeteoCareTheme

object AppButtonDefaults {
    @Composable
    fun primaryButtonColors() = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.tertiary,
        contentColor = MaterialTheme.colorScheme.surface,
        disabledContentColor = MaterialTheme.colorScheme.errorContainer,
        disabledContainerColor = MaterialTheme.colorScheme.error
    )

    @Composable
    fun secondaryButtonColors() = ButtonDefaults.buttonColors()

    @Composable
    fun textStyle(size: ButtonSize): TextStyle {
        return when (size) {
            ButtonSize.Small -> MaterialTheme.typography.titleSmall
            ButtonSize.Medium -> MaterialTheme.typography.titleMedium
            ButtonSize.Large -> MaterialTheme.typography.titleLarge
        }
    }

    @Composable
    fun contentPadding(size: ButtonSize): PaddingValues {
        return when (size) {
            ButtonSize.Small -> PaddingValues(
                MeteoCareTheme.spacing.sm,
                MeteoCareTheme.spacing.xs
            )
            ButtonSize.Medium -> PaddingValues(
                MeteoCareTheme.spacing.md,
                MeteoCareTheme.spacing.xs
            )
            ButtonSize.Large -> PaddingValues(
                MeteoCareTheme.spacing.md,
                MeteoCareTheme.spacing.sm
            )
        }
    }

    val shape
        @Composable
        get() = MaterialTheme.shapes.medium
}