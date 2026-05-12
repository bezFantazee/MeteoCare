package com.practicum.mateocare.ai.data.dto

import com.practicum.mateocare.forecast.data.dto.Response

data class GeminiResponse (
    val candidates: List<Candidate>
) : Response()