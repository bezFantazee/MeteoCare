package com.practicum.mateocare.ui.feature.profile.components.meteoStatus

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.practicum.mateocare.ui.components.blocks.PrimaryTextBlock
import com.practicum.mateocare.ui.components.buttons.PrimaryButton
import com.practicum.mateocare.ui.components.text.ErrorText
import com.practicum.mateocare.ui.feature.components.survey.FullSurvey
import com.practicum.mateocare.ui.feature.profile.presentation.models.SurveyQuestions

@Composable
fun MeteoStatusSurvey(
    onFinishButtonClicked: (Map<Int, String>) -> Unit
) {
    //открытие окна с опросом
    var showSurvey by remember { mutableStateOf(false) }

    ErrorText(
        text = "Ваш статус еще не определен"
    )

    PrimaryTextBlock(
        text = "Пройдите опрос для определения вашего статуса",
        modifier = Modifier
            .fillMaxWidth()
    )

    PrimaryButton(
        text = "Пройти",
        onClick = { showSurvey = true }
    )

    if (showSurvey) {
        FullSurvey(
            onDismissRequest = { showSurvey = false },
            questions = SurveyQuestions.Primary,
            onFinishButtonClicked = onFinishButtonClicked
        )
    }
}