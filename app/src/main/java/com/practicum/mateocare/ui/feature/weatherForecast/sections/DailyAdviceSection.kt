package com.practicum.mateocare.ui.feature.weatherForecast.sections

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.practicum.mateocare.forecast.domain.models.WeatherForecast
import com.practicum.mateocare.ui.components.blocks.ErrorTextBlock
import com.practicum.mateocare.ui.feature.weatherForecast.components.dailyAdvice.DailyAdviceLoading
import com.practicum.mateocare.ui.feature.weatherForecast.components.dailyAdvice.DailyAdviceResult
import com.practicum.mateocare.ui.feature.weatherForecast.components.dailyAdvice.DailyAdviceSurvey
import com.practicum.mateocare.ui.feature.weatherForecast.presentation.state.DailyAdviceState

@Composable
fun DailyAdviceSection(
    adviceState: DailyAdviceState,
    fullForecast: WeatherForecast?,
    getDailyAdviceAndStatus: (WeatherForecast?, String?) -> Unit
) {
    when (adviceState) {
        is DailyAdviceState.Loading -> {
            DailyAdviceLoading()
        }

        is DailyAdviceState.Error -> {
            ErrorTextBlock(adviceState.message, modifier = Modifier.fillMaxWidth())
        }

        is DailyAdviceState.Unauthorized -> {}
        is DailyAdviceState.WaitingSurvey -> {
            if (fullForecast == null) {
                DailyAdviceLoading()
            } else {
                DailyAdviceSurvey(
                    onFinishButtonClicked = { answers ->
                        getDailyAdviceAndStatus(fullForecast, answers)
                    }
                )
            }
        }

        is DailyAdviceState.Content -> {
            DailyAdviceResult(
                advice = adviceState.userDailyStatus?.advice,
                dailyStatus = adviceState.userDailyStatus?.status
            )
        }
    }
}