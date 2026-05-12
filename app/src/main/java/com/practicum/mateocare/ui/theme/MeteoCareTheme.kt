package com.practicum.mateocare.ui.theme

import androidx.compose.runtime.Composable
import com.practicum.mateocare.ui.theme.spacing.AppSpacing
import com.practicum.mateocare.ui.theme.spacing.LocalSpacing

object MeteoCareTheme {
    val spacing: AppSpacing
        @Composable
        get() = LocalSpacing.current
}