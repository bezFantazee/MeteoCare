package com.practicum.mateocare.ui.feature.profile.components.meteoStatus

import androidx.compose.runtime.Composable
import com.practicum.mateocare.ui.components.blocks.ErrorTextBlock
import com.practicum.mateocare.ui.components.buttons.SecondaryButton
import com.practicum.mateocare.ui.feature.components.state.ErrorMessage

@Composable
fun AiStatusError(
    message: String,
    onReapedButtonClicked: () -> Unit
) {
    ErrorTextBlock(message)

    SecondaryButton(
        text = "Повторить",
        onClick = onReapedButtonClicked
    )
}