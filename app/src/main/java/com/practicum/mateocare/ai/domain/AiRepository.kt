package com.practicum.mateocare.ai.domain

import com.practicum.mateocare.ai.domain.models.GetAiResult
import com.practicum.mateocare.ai.domain.models.MeteoAdviceResult
import com.practicum.mateocare.profile.domain.models.MeteoStatus
import kotlinx.coroutines.flow.Flow

interface AiRepository {
    suspend fun generateMeteoAdvice(prompt: String): GetAiResult
}