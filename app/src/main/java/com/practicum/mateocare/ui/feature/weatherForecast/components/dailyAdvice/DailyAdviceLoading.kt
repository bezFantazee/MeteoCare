package com.practicum.mateocare.ui.feature.weatherForecast.components.dailyAdvice

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.practicum.mateocare.ui.components.animation.TypingDotsAnimation
import com.practicum.mateocare.ui.theme.MeteoCareTheme

@Composable
fun DailyAdviceLoading() {
    TypingDotsAnimation(
        modifier = Modifier
            .padding(bottom = MeteoCareTheme.spacing.md)
            .fillMaxWidth()
            .clip(shape = MaterialTheme.shapes.medium)
            .background(color = MaterialTheme.colorScheme.tertiaryContainer)
            .padding(MeteoCareTheme.spacing.md)
    )
}