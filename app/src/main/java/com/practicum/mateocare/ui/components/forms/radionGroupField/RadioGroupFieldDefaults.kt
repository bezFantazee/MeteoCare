package com.practicum.mateocare.ui.components.forms.radionGroupField

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import com.practicum.mateocare.ui.theme.MeteoCareTheme

object AppRadioGroupFieldDefaults {
    //vertical
    val VerticalTextColor
        @Composable
        get() = MaterialTheme.colorScheme.onSurface

    val VerticalRadioButtonColors
        @Composable
        get() = RadioButtonDefaults.colors(
            selectedColor = MaterialTheme.colorScheme.tertiaryContainer,
            unselectedColor = MaterialTheme.colorScheme.outline
        )

    val VerticalTextStyle
        @Composable
        get() = MaterialTheme.typography.bodyMedium

    val VerticalSpacing: Dp
        @Composable
        get() = MeteoCareTheme.spacing.sm

    //horizontal
    val HorizontalTextColor
        @Composable
        get() = MaterialTheme.colorScheme.primary

    val HorizontalRadioButtonColors
        @Composable
        get() = RadioButtonDefaults.colors()

    val HorizontalTextStyle
        @Composable
        get() = MaterialTheme.typography.bodyMedium

    val HorizontalSpacing: Dp
        @Composable
        get() = MeteoCareTheme.spacing.sm
}