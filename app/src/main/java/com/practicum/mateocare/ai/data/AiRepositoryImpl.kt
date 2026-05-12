package com.practicum.mateocare.ai.data

import android.util.Log
import com.google.gson.Gson
import com.practicum.mateocare.ai.data.dto.Content
import com.practicum.mateocare.ai.data.dto.GeminiRequest
import com.practicum.mateocare.ai.data.dto.GeminiResponse
import com.practicum.mateocare.ai.data.dto.Part
import com.practicum.mateocare.ai.data.mapper.AiResponseMapper
import com.practicum.mateocare.ai.domain.AiRepository
import com.practicum.mateocare.ai.domain.models.GetAiResult
import com.practicum.mateocare.ai.domain.models.MeteoAdviceResult
import com.practicum.mateocare.forecast.data.NetworkClient
import com.practicum.mateocare.profile.domain.models.MeteoStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AiRepositoryImpl(
    private val networkClient: NetworkClient,
    private val mapper: AiResponseMapper
) : AiRepository {

    override suspend fun generateMeteoAdvice(prompt: String): Flow<GetAiResult> = flow {

        try {

            val request = GeminiRequest(
                contents = listOf(
                    Content(
                        parts = listOf(
                            Part(prompt)
                        )
                    )
                )
            )

            val response = networkClient.doRequest(request)

            when (response.resultCode) {

                200 -> {

                    val geminiResponse = response as? GeminiResponse

                    Log.d("AI_RAW", geminiResponse.toString())
                    if (geminiResponse == null) {
                        emit(GetAiResult.ServerError)
                        return@flow
                    }

                    val text = geminiResponse.candidates
                        .firstOrNull()
                        ?.content
                        ?.parts
                        ?.firstOrNull()
                        ?.text
                    Log.d("AI_RAW", text ?: "null")

                    if (text.isNullOrBlank()) {
                        emit(GetAiResult.NoResults)
                        return@flow
                    }

                    val result = mapper.toMeteoAdvice(text)

                    emit(
                        GetAiResult.Success(result)
                    )
                }

                400, 401 -> {
                    emit(GetAiResult.NoResults)
                }

                -1 -> {
                    emit(GetAiResult.NetworkError)
                }

                else -> {
                    emit(GetAiResult.ServerError)
                }
            }

        } catch (e: Exception) {
            emit(GetAiResult.ServerError)
        }
    }
}