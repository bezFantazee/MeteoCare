package com.practicum.mateocare.ai.data

import android.R.attr.data
import com.practicum.mateocare.ai.data.dto.GeminiRequest
import com.practicum.mateocare.forecast.data.NetworkClient
import com.practicum.mateocare.forecast.data.dto.Response
import java.lang.Exception

class GeminiRetrofitService(
    private val api: GeminiApi
) : NetworkClient {

    private val apiKey = "AQ.Ab8RN6K7I3uYDDKbUrRHWncABCOquZg-0YmF5VxtBKzgX04MRQ"

    override suspend fun doRequest(dto: Any): Response {
        return try {

            val request = dto as GeminiRequest

            val response = api.generate(
                apiKey,
                request
            )

            if (response.isSuccessful) {
                response.body()?.apply { resultCode = 200 } ?: Response().apply { resultCode = 204 }
            } else {
                Response().apply { resultCode = response.code() }
            }
        } catch (e: Exception) {
            Response().apply { resultCode = -1 }
        }
    }
}