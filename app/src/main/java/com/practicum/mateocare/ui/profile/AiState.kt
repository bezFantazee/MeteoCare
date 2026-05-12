package com.practicum.mateocare.ui.profile

import com.practicum.mateocare.profile.domain.models.MeteoStatus

sealed interface AiState {
    object Initial : AiState
    object Loading : AiState
    class Error(
        val message: String
    ) : AiState
    class Content(
        val meteoStatus: MeteoStatus,
        val advice: String
    ) : AiState
}