package com.practicum.mateocare.ui.feature.weatherForecast.presentation.state

import com.practicum.mateocare.profile.domain.models.UserDailyStatus

sealed interface DailyAdviceState {
    data object Unauthorized : DailyAdviceState

    data object WaitingSurvey: DailyAdviceState

    data object Loading: DailyAdviceState

    data class Content(
        val userDailyStatus: UserDailyStatus?
    ) : DailyAdviceState

    data class Error(
        val message: String
    ) : DailyAdviceState
}