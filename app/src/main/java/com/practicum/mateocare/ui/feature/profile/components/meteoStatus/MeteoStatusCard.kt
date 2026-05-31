package com.practicum.mateocare.ui.feature.profile.components.meteoStatus

import androidx.compose.runtime.Composable
import com.practicum.mateocare.ui.components.blocks.PrimaryTextBlock
import com.practicum.mateocare.ui.components.blocks.SecondaryTextBlock

@Composable
fun MeteoStatusCard(
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