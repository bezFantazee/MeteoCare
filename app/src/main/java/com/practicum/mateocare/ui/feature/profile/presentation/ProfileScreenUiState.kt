package com.practicum.mateocare.ui.feature.profile.presentation

import com.practicum.mateocare.profile.domain.models.MeteoStatus
import com.practicum.mateocare.ui.feature.profile.presentation.models.MeteoStatusUi
import com.practicum.mateocare.ui.feature.profile.presentation.models.ProfileUi

sealed interface ProfileScreenUiState{
    object Loading : ProfileScreenUiState
    sealed interface Unauthorized : ProfileScreenUiState {
        object Initial: Unauthorized
        object RegisterMode: Unauthorized
        object LoginMode: Unauthorized
    }
    data class Authorized(
        val profile: ProfileUi,
        val meteoStatus: MeteoStatusState
    ) : ProfileScreenUiState

    class Error(val message: String) : ProfileScreenUiState
}