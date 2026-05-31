package com.practicum.mateocare.ui.feature.components.survey

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.practicum.mateocare.ui.components.buttons.PrimaryButton
import com.practicum.mateocare.ui.feature.profile.presentation.models.Question
import com.practicum.mateocare.ui.theme.MeteoCareTheme
import kotlin.collections.forEach
import kotlin.collections.set

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullSurvey(
    onDismissRequest: () -> Unit,
    questions: List<Question>,
    onFinishButtonClicked: (Map<Int, String>) -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    val scrollState = rememberScrollState()

    val allAnswers = remember(questions) {
        mutableStateMapOf<Int, String>().apply {
            questions.forEach { question ->
                put(
                    question.id,
                    question.answers.first()
                )
            }
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(MeteoCareTheme.spacing.md),
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .padding(MeteoCareTheme.spacing.md)
        ) {
            Text(
                text = "Пройдите опрос на метеозависимость",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            questions.forEach { question ->
                SurveyElement(
                    title = question.text,
                    options = question.answers,

                    selectedOption = allAnswers[question.id] ?: question.answers.first(),

                    onSelectedChanged = { ans ->
                        allAnswers[question.id] = ans
                    }
                )
            }

            PrimaryButton(
                text = "Завершить",
                onClick = { onFinishButtonClicked(allAnswers) }
            )
        }
    }
}