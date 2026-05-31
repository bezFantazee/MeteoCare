package com.practicum.mateocare.ui.feature.weatherForecast.components.dailyAdvice

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.practicum.mateocare.ui.components.blocks.PrimaryTextBlock
import com.practicum.mateocare.ui.components.text.BodyText
import com.practicum.mateocare.ui.feature.components.dailyHealth.DailyHealth
import com.practicum.mateocare.ui.theme.MeteoCareTheme

@Composable
fun DailyAdviceResult(
    advice: String?,
    dailyStatus: String?
) {
    PrimaryTextBlock(
        text = advice ?: "Не определен",
        modifier = Modifier
            .padding(bottom = MeteoCareTheme.spacing.md)
            .fillMaxWidth()
    )

    Row(
        horizontalArrangement = Arrangement.spacedBy(MeteoCareTheme.spacing.sm),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        BodyText(
            text = "Ваше состояние: "
        )
        DailyHealth(dailyStatus ?: "default")
    }
}