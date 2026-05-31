package com.practicum.mateocare.ui.feature.weatherForecast.components.dailyAdvice

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.practicum.mateocare.ui.components.blocks.PrimaryTextBlock
import com.practicum.mateocare.ui.components.buttons.PrimaryButton
import com.practicum.mateocare.ui.feature.components.survey.FullSurvey
import com.practicum.mateocare.ui.feature.profile.presentation.models.SurveyQuestions
import com.practicum.mateocare.ui.theme.MeteoCareTheme

private val dailyQuestions = SurveyQuestions.Daily
@Composable
fun DailyAdviceSurvey(
    onFinishButtonClicked: (String) -> Unit
) {
    //открытие окна с опросом
    var showSurvey by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        PrimaryTextBlock(
            text = "Пройдите ежедневный опрос для улучшения ежедневных советов",
            modifier = Modifier
                .padding(bottom = MeteoCareTheme.spacing.md)
                .fillMaxWidth()
        )

        PrimaryButton(
            text = "Пройти",
            onClick = { showSurvey = true }
        )
    }

    if(showSurvey) {
        FullSurvey(
            { showSurvey = false },
            dailyQuestions,
            { answers ->
                showSurvey = false
                val finalReport = answers.map { "${it.key}: ${it.value}"}.joinToString("; ")

                onFinishButtonClicked(finalReport)
            }
        )
    }
}