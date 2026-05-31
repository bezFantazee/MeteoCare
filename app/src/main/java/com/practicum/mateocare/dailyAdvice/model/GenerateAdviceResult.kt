package com.practicum.mateocare.dailyAdvice.model

import com.practicum.mateocare.profile.domain.models.UserDailyStatus

sealed interface GenerateAdviceResult {
    data object NoUserDataError : GenerateAdviceResult

    data object GenerateError : GenerateAdviceResult

    data class Success(
        val dailyStatus: UserDailyStatus
    ) : GenerateAdviceResult
}