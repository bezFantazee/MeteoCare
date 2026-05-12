package com.practicum.mateocare.forecast.data.api

import com.practicum.mateocare.forecast.data.dto.WeatherForecastResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MeteoApiService {
    @GET("v1/forecast?hourly=temperature_2m,apparent_temperature,relative_humidity_2m,pressure_msl,windspeed_10m,precipitation,dewpoint_2m&timezone=auto")
    suspend fun getWeatherForecast(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double
    ): Response<WeatherForecastResponse>
}