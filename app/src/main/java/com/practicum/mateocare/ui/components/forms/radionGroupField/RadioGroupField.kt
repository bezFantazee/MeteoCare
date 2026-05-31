package com.practicum.mateocare.ui.components.forms.radionGroupField

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

@Composable
private fun RadioButtonRow(
    option: String,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    radioButtonColors: RadioButtonColors,
    textStyle: TextStyle,
    textColor: Color,
    spacing: Dp,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(spacing),
        modifier = modifier
            .selectable(
                selected = option == selectedOption,
                onClick = {
                    onOptionSelected(option)
                }
            )
    ) {
        RadioButton(
            selected = option == selectedOption,
            onClick = null,
            colors = radioButtonColors
        )

        Text(
            text = option,
            style = textStyle,
            color = textColor
        )
    }
}

@Composable
fun RadioGroupVerticalField(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    arrangement: Arrangement.Vertical,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = arrangement,
        modifier = modifier
    ) {
        options.forEach { option ->
            RadioButtonRow(
                option = option,
                selectedOption = selectedOption,
                onOptionSelected = onOptionSelected,
                radioButtonColors = AppRadioGroupFieldDefaults.VerticalRadioButtonColors,
                textStyle = AppRadioGroupFieldDefaults.VerticalTextStyle,
                textColor = AppRadioGroupFieldDefaults.VerticalTextColor,
                spacing = AppRadioGroupFieldDefaults.VerticalSpacing,
                modifier = Modifier
                    .fillMaxWidth(),
            )
        }
    }
}

@Composable
fun RadioButtonHorizontalField(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    arrangement: Arrangement.Horizontal,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = arrangement,
        modifier = modifier
    ) {
        options.forEach { option ->
            RadioButtonRow(
                option = option,
                selectedOption = selectedOption,
                onOptionSelected = onOptionSelected,
                radioButtonColors = AppRadioGroupFieldDefaults.HorizontalRadioButtonColors,
                textStyle = AppRadioGroupFieldDefaults.HorizontalTextStyle,
                textColor = AppRadioGroupFieldDefaults.HorizontalTextColor,
                spacing = AppRadioGroupFieldDefaults.HorizontalSpacing
            )
        }
    }
}
