package com.practicum.mateocare.forecast.data

import android.util.Log
import com.practicum.mateocare.forecast.data.dto.WeatherForecastRequest
import com.practicum.mateocare.forecast.data.dto.WeatherForecastResponse
import com.practicum.mateocare.forecast.domain.GetWeatherForecastRepository
import com.practicum.mateocare.forecast.domain.models.GetForecastResult
import com.practicum.mateocare.forecast.domain.models.WeatherForecast
import com.practicum.mateocare.forecast.domain.models.WeatherForecastUnits
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetWeatherForecastRepositoryImpl(
    private val networkClient: NetworkClient
) : GetWeatherForecastRepository {
    override fun getWeatherForecast(latitude: Double, longitude: Double): Flow<GetForecastResult> = flow {
        val response = networkClient.doRequest(WeatherForecastRequest(latitude, longitude))
        Log.d("12qw", response.resultCode.toString())
        when (response.resultCode) {
           200 -> {
                with(response as WeatherForecastResponse) {
                    if(hourly == null || hourly.time.isNullOrEmpty()) {
                        emit(GetForecastResult.NoResults)
                    } else {
                        val data = WeatherForecast(
                            time=hourly.time,
                            temperature=hourly.temperature2m ?: emptyList(),
                            apparentTemperature=hourly.apparentTemperature ?: emptyList(),
                            relativeHumidity=hourly.relativeHumidity2m ?: emptyList(),
                            dewPoint=hourly.dewpoint2m ?: emptyList(),
                            pressure=hourly.pressureMsl ?: emptyList(),
                            windSpeed=hourly.windspeed10m ?: emptyList(),
                            precipitation=hourly.precipitation ?: emptyList(),
                            weatherCode = hourly.weatherCode ?: emptyList()
                        )
                        val dataUnits = WeatherForecastUnits(
                            apparentTemperature=hourlyUnits?.apparentTemperature,
                            dewPoint=hourlyUnits?.dewpoint2m,
                            precipitation=hourlyUnits?.precipitation,
                            pressure=hourlyUnits?.pressureMsl,
                            relativeHumidity=hourlyUnits?.relativeHumidity2m,
                            temperature=hourlyUnits?.temperature2m,
                            time=hourlyUnits?.time,
                            windSpeed=hourlyUnits?.windspeed10m
                        )
                        emit(GetForecastResult.Success(data, dataUnits))
                    }
                }
            }
            400, 404 -> emit(GetForecastResult.NoResults)
            -1 -> emit(GetForecastResult.NetworkError)
            else -> emit(GetForecastResult.ServerError)
        }
    }
}
