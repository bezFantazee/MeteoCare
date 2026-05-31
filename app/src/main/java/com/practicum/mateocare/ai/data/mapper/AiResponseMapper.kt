package com.practicum.mateocare.ai.data.mapper

import com.google.gson.Gson
import com.practicum.mateocare.ai.domain.models.MeteoAdviceResult

class AiResponseMapper {
    fun toMeteoAdvice(raw: String): MeteoAdviceResult {

        val fullJson = extractJson(raw)

        return Gson().fromJson(fullJson, MeteoAdviceResult::class.java)
    }

    private fun extractJson(text: String): String {
        val regex = Regex("\\{[\\s\\S]*\\}")
        return regex.find(text)?.value ?: throw IllegalStateException("No JSON found")
    }
}