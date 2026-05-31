package com.practicum.mateocare.ui.feature.profile.presentation

import com.practicum.mateocare.ui.feature.profile.presentation.models.MeteoStatusUi

sealed interface MeteoStatusState {
    object Empty : MeteoStatusState
    object Loading : MeteoStatusState
    data class Error(
        val message: String
    ) : MeteoStatusState
    data class Content(
        val userMeteoStatus: MeteoStatusUi
    ) : MeteoStatusState
}