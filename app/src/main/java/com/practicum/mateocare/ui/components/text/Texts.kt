package com.practicum.mateocare.ui.components.text

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign

@Composable
private fun BaseText(
    text: String,
    color: Color,
    modifier: Modifier = Modifier,
    style: TextStyle,
    align: TextAlign? = null
) {
    Text(
        text = text,
        color = color,
        textAlign = align,
        style = style,
        modifier = modifier
    )
}

@Composable
fun TitleText(text: String, modifier: Modifier = Modifier){
    BaseText(
        text = text.uppercase(),
        color = AppTextDefaults.TitleTextColor,
        style = AppTextDefaults.textStyle(TextSize.Large),
        modifier = modifier
    )
}

@Composable
fun BodyText(text: String, modifier: Modifier = Modifier, align: TextAlign? = null) {
    BaseText(
        text = text,
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier,
        style = AppTextDefaults.textStyle(TextSize.Medium),
        align = align
    )
}

@Composable
fun ErrorText(text: String, modifier: Modifier = Modifier) {
    BaseText(
        text = text,
        color = MaterialTheme.colorScheme.error,
        style = AppTextDefaults.textStyle(TextSize.Medium),
        modifier = modifier
    )
}