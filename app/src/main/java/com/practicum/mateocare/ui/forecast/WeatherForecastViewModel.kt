package com.practicum.mateocare.ui.forecast

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.mateocare.forecast.domain.GetWeatherForecastInteractor
import com.practicum.mateocare.forecast.domain.models.GetForecastResult
import com.practicum.mateocare.forecast.domain.models.WeatherForecast
import com.practicum.mateocare.forecast.domain.models.WeatherForecastUnits
import com.practicum.mateocare.geoLocation.domain.LocationInteractor
import com.practicum.mateocare.ui.forecast.models.WeatherParameterUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class WeatherForecastViewModel(
    private val getWeatherForecastInteractor: GetWeatherForecastInteractor,
    private val locationInteractor: LocationInteractor
) : ViewModel() {
    private val _uiState = MutableStateFlow<WeatherForecastState>(WeatherForecastState.Loading(null, null))
    val uiState: StateFlow<WeatherForecastState> = _uiState.asStateFlow()

    fun onPermissionGranted() {
        fetchLocationAndWeather()
    }

    fun getTimes(): List<String> {
        val state = uiState.value
        if( state !is WeatherForecastState.Content) return emptyList()

        val inputFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
        val outputFormatter = DateTimeFormatter.ofPattern("HH:mm")

        val fulltime = state.fullForecasts.time.filterIndexed { index, _ -> index % 3 == 0 && index <= 24 }
        return fulltime.map {item ->

            LocalDateTime.parse(item, inputFormatter).format(outputFormatter)
        }
    }

    fun getDetailsForecast(itemId: Int): List<Any> {
        val state = uiState.value
        if( state !is WeatherForecastState.Content) return emptyList()

        return with(state.fullForecasts){
           when(itemId){
                0 -> {
                    temperature?.filterIndexed { index, _ -> index % 3 == 0 && index <= 24 } ?: emptyList()
                }

                1 -> {
                    windSpeed?.filterIndexed { index, _ -> index % 3 == 0 && index <= 24 } ?: emptyList()
                }

                2 -> {
                    relativeHumidity?.filterIndexed { index, _ -> index % 3 == 0 && index <= 24 } ?: emptyList()
                }

                3 -> {
                    Log.d("13qw", dewPoint.toString())
                    dewPoint?.filterIndexed { index, _ -> index % 3 == 0 && index <= 24 } ?: emptyList()
                }

                4 -> {
                    pressure?.filterIndexed { index, _ -> index % 3 == 0 && index <= 24 } ?: emptyList()
                }

                else -> {
                   emptyList()
                }
            }
        }
    }

    private fun fetchLocationAndWeather() {
        viewModelScope.launch {
            _uiState.update { WeatherForecastState.Loading(it.latitude, it.longitude) }

            locationInteractor.getLocationUpdates(interval = 36000000L)
                .catch { e ->
                    _uiState.update {  WeatherForecastState.Error("Ошибка GPS") }
                }
                .collect { location ->
                    Log.d("12qw", location.latitude.toString() + " " + location.longitude.toString())
                    loadWeather(location.latitude, location.longitude)
                }
        }
    }

    private fun loadWeather(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            getWeatherForecastInteractor
                .getWeatherForecast(latitude, longitude)
                .collect { result ->
                    when (result) {
                        is GetForecastResult.Success -> {
                            val currentIndexHour = getCurrentHoutIndex(result.forecasts.time)
                            _uiState.update {
                                WeatherForecastState.Content(
                                    forecasts = mapToUiModels(
                                        result.forecasts,
                                        result.forecastUnits,
                                        currentIndexHour
                                    ),
                                    fullForecasts = result.forecasts,
                                    latitude = latitude,
                                    longitude = longitude
                                )
                            }
                        }
                        is GetForecastResult.NoResults -> {
                            _uiState.update {
                                WeatherForecastState.Empty("Ничего не нашлось")
                            }
                        }
                        is GetForecastResult.NetworkError -> {
                            _uiState.update {
                                WeatherForecastState.Error("Нет подключения к интернету")
                            }
                        }
                        is GetForecastResult.ServerError -> {
                            _uiState.update {
                                WeatherForecastState.Error("Что-то пошло не так")
                            }
                        }
                    }
                }
        }
    }

    private fun mapToUiModels(forecast: WeatherForecast, units: WeatherForecastUnits, hourIndex: Int): List<WeatherParameterUiModel> {
        return listOf(
            WeatherParameterUiModel(
                id = 0,
                name = "Температура",
                value = "${forecast.temperature?.get(hourIndex) ?: "Ошибка"}",
                unit = units.temperature ?: "Ошибка"
            ),
            WeatherParameterUiModel(
                id = 1,
                name = "Скорость ветра",
                value = "${forecast.windSpeed?.get(hourIndex) ?: "Ошибка"}",
                unit = units.windSpeed ?: "Ошибка"
            ),
            WeatherParameterUiModel(
                id = 2,
                name = "Влажность",
                value = "${forecast.relativeHumidity?.get(hourIndex) ?: "Ошибка"}",
                unit = units.relativeHumidity ?: "Ошибка"
            ),
            WeatherParameterUiModel(
                id = 3,
                name = "Точка россы",
                value = "${forecast.dewPoint?.get(hourIndex) ?: "Ошибка"}",
                unit = units.dewPoint ?: "Ошибка"
            ),
            WeatherParameterUiModel(
                id = 4,
                name="Давление",
                value = "${forecast.pressure?.get(hourIndex) ?: "Ошибка"}",
                unit = units.pressure ?: "Ошибка"
            )
        )
    }

    private fun getCurrentHoutIndex(timeList: List<String>): Int {
        val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
        val now = LocalDateTime.now()

        return timeList.indexOfFirst { timeString ->
            val time = LocalDateTime.parse(timeString, formatter)
            time.hour == now.hour && time.dayOfMonth == now.dayOfMonth
        }
    }
}