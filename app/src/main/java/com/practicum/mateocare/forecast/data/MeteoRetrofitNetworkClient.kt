package com.practicum.mateocare.forecast.data

import com.practicum.mateocare.forecast.data.api.MeteoApiService
import com.practicum.mateocare.forecast.data.dto.Response
import com.practicum.mateocare.forecast.data.dto.WeatherForecastRequest
import java.lang.Exception

class MeteoRetrofitNetworkClient(
    private val meteoService: MeteoApiService
): NetworkClient {
    override suspend fun doRequest(dto: Any): Response {
        if (dto !is WeatherForecastRequest) {
            return Response().apply {resultCode = 400}
        }

        return try {
            val response = meteoService.getWeatherForecast(dto.latitude, dto.longitude)

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