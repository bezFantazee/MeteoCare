package com.practicum.mateocare.ui.components.text

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle

object AppTextDefaults {
    val TitleTextColor
        @Composable
        get() = MaterialTheme.colorScheme.tertiary

    val BodyTextColor
        @Composable
        get() = MaterialTheme.colorScheme.primary

    val ErrorTextColor
        @Composable
        get() = MaterialTheme.colorScheme.error

    @Composable
    fun textStyle(textSize: TextSize): TextStyle {
        return when(textSize) {
            TextSize.Small -> MaterialTheme.typography.bodyLarge
            TextSize.Medium -> MaterialTheme.typography.titleLarge
            TextSize.Large -> MaterialTheme.typography.headlineLarge
        }
    }
}