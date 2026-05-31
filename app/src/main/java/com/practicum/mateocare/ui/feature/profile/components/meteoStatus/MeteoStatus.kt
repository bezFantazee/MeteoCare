package com.practicum.mateocare.ui.feature.profile.components.meteoStatus

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.practicum.mateocare.ui.components.blocks.PrimaryTextBlock
import com.practicum.mateocare.ui.components.blocks.SecondaryTextBlock
import com.practicum.mateocare.ui.components.text.BodyText
import com.practicum.mateocare.ui.feature.components.dailyHealth.DailyHealth
import com.practicum.mateocare.ui.feature.components.dailyHealth.DailyHealthStatus
import kotlin.collections.forEach

@Composable
fun MeteoStatus(
    meteoStatusType: String,
    mainAdvice: String
) {
    SecondaryTextBlock(
        text = "Метео статус: " + meteoStatusType
    )

    PrimaryTextBlock(
        text = mainAdvice
    )
}