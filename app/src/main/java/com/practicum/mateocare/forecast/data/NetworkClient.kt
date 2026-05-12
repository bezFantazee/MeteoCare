package com.practicum.mateocare.forecast.data

import com.practicum.mateocare.forecast.data.dto.Response

interface NetworkClient {
    suspend fun doRequest(dto: Any): Response
}