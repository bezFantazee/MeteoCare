package com.practicum.mateocare.ui.components.cards

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
private fun BaseCardHeader(
    text: String,
    color: Color,
    modifier: Modifier
){
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        color = color,
        modifier = modifier
    )
}

@Composable
fun PrimaryCardHeader(
    text: String,
    modifier: Modifier = Modifier
) {
    BaseCardHeader(
        text = text,
        color = MaterialTheme.colorScheme.onPrimaryContainer,
        modifier = modifier
    )
}