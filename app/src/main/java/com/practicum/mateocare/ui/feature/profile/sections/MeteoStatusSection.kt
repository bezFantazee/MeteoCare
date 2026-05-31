package com.practicum.mateocare.ui.feature.profile.sections

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.practicum.mateocare.ui.feature.components.state.LoadingScreen
import com.practicum.mateocare.ui.feature.profile.components.meteoStatus.AiStatusError
import com.practicum.mateocare.ui.feature.profile.components.meteoStatus.MeteoStatusSurvey
import com.practicum.mateocare.ui.feature.profile.components.meteoStatus.MeteoStatusCard
import com.practicum.mateocare.ui.feature.profile.components.meteoStatus.HealthHistoryRow
import com.practicum.mateocare.ui.feature.profile.presentation.MeteoStatusState

@Composable
fun MeteoStatusSection(
    meteoStatusState: MeteoStatusState,
    generateMeteoStatus: (String) -> Unit
){
    // Переменная для хранения ответов на случай ошибки
    var lastAnswers by remember { mutableStateOf("") }

    when(meteoStatusState) {
        is MeteoStatusState.Error -> {
            AiStatusError(
                message = meteoStatusState.message,
                onReapedButtonClicked = {
                    Log.d("12wr", lastAnswers.toString())
                    if (lastAnswers.isNotEmpty()) {
                        generateMeteoStatus(lastAnswers)
                    }

                }
            )
        }

        is MeteoStatusState.Loading -> {
            LoadingScreen()
        }

        is MeteoStatusState.Empty -> {
            MeteoStatusSurvey(
                onFinishButtonClicked = { allAnswers ->
                    val finalReport = allAnswers.map { "${it.key}: ${it.value}" }.joinToString("; ")
                    lastAnswers = finalReport
                    generateMeteoStatus(finalReport)
                }
            )
        }

        is MeteoStatusState.Content -> {
            MeteoStatusCard(
                meteoStatusType = meteoStatusState.userMeteoStatus.meteoStatus.type,
                mainAdvice = meteoStatusState.userMeteoStatus.advice
            )

            HealthHistoryRow(meteoStatusState.userMeteoStatus.healthHistory)
        }
    }
}