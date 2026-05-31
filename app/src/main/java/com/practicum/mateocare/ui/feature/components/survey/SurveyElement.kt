package com.practicum.mateocare.ui.feature.components.survey

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.practicum.mateocare.ui.components.forms.radionGroupField.RadioGroupVerticalField

@Composable
fun SurveyElement(
    title: String,
    options: List<String>,
    selectedOption: String,
    onSelectedChanged: (String) -> Unit){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(16.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        RadioGroupVerticalField(
            options = options,
            selectedOption = selectedOption,
            onOptionSelected = onSelectedChanged,
            arrangement = Arrangement.SpaceBetween
        )
    }
}