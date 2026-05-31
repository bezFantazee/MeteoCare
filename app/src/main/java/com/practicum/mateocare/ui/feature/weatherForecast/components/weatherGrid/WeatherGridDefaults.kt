package com.practicum.mateocare.ui.feature.weatherForecast.components.weatherGrid

import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.IntSize
import com.practicum.mateocare.ui.theme.MeteoCareTheme

object WeatherGridDefaults {

    val ItemSpacing
        @Composable
        get() = MeteoCareTheme.spacing.md

    val GridSpacing
        @Composable
        get() = MeteoCareTheme.spacing.xl

    val CardAspectRatio = 0.9f

    fun collapsedSpring(): FiniteAnimationSpec<IntSize> = spring(
        dampingRatio = Spring.DampingRatioLowBouncy,
        stiffness = Spring.StiffnessLow
    )

    fun expandedSpring(): FiniteAnimationSpec<IntSize> = spring(
        dampingRatio = Spring.DampingRatioNoBouncy,
        stiffness = Spring.StiffnessMedium
    )
}