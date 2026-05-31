package com.practicum.mateocare.ui.components.buttons

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
private fun BaseButton(
    colors: ButtonColors,
    text: String,
    onClick: () -> Unit,
    size: ButtonSize = ButtonSize.Medium,
    modifier: Modifier
) {
    Button(
        colors = colors,
        onClick = onClick,
        shape = AppButtonDefaults.shape,
        contentPadding = AppButtonDefaults.contentPadding(size),
        modifier = modifier
    ) {
        Text(
            text = text.uppercase(),
            style = AppButtonDefaults.textStyle(size)
        )
    }
}

@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    BaseButton(
        colors = AppButtonDefaults.secondaryButtonColors(),
        text = text,
        onClick = onClick,
        size = ButtonSize.Medium,
        modifier = modifier
    )
}

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    BaseButton(
        colors = AppButtonDefaults.primaryButtonColors(),
        text = text,
        onClick = onClick,
        size = ButtonSize.Large,
        modifier = modifier
    )
}