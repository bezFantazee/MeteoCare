package com.practicum.mateocare.ui.components.blocks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign

@Composable
private fun BaseTextBlock(
    text: String,
    colors: TextBlockColors,
    modifier: Modifier
) {
    Text(
        text = text,
        color = colors.textColor,
        style = AppTextBlockDefaults.TextStyle,
        textAlign = TextAlign.Justify,
        modifier = modifier
            .clip(shape= AppTextBlockDefaults.Shape)
            .background(color= colors.backgroundColor)
            .padding(AppTextBlockDefaults.ContentPadding)
    )
}

@Composable
fun PrimaryTextBlock(
    text: String,
    modifier: Modifier = Modifier
) {
    BaseTextBlock(
        text = text,
        colors = AppTextBlockDefaults.primaryColors(),
        modifier = modifier
    )
}

@Composable
fun SecondaryTextBlock(
    text: String,
    modifier: Modifier = Modifier
) {
    BaseTextBlock(
        text = text,
        colors = AppTextBlockDefaults.secondaryColors(),
        modifier = modifier
    )
}

@Composable
fun ErrorTextBlock(
    text: String,
    modifier: Modifier = Modifier
) {
    BaseTextBlock(
        text = text,
        colors = AppTextBlockDefaults.errorColors(),
        modifier = modifier
    )
}