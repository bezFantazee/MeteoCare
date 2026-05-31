package com.practicum.mateocare.ui.feature.components.state

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.practicum.mateocare.ui.components.blocks.ErrorTextBlock
import com.practicum.mateocare.ui.components.text.ErrorText

@Composable
fun ErrorMessage(message: String, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
    ) {
        ErrorTextBlock(
            text = message
        )
    }
}