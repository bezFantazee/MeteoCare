package com.practicum.mateocare.ui.feature.weatherForecast.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.mateocare.dailyAdvice.DailyAdviceInteractor
import com.practicum.mateocare.dailyAdvice.model.GenerateAdviceResult
import com.practicum.mateocare.forecast.domain.GetWeatherForecastInteractor
import com.practicum.mateocare.forecast.domain.models.GetForecastResult
import com.practicum.mateocare.forecast.domain.models.WeatherForecast
import com.practicum.mateocare.geoLocation.domain.LocationInteractor
import com.practicum.mateocare.profile.domain.firebase.ProfileInteractor
import com.practicum.mateocare.profile.domain.models.User
import com.practicum.mateocare.ui.feature.weatherForecast.presentation.mappers.ForecastUiMapper
import com.practicum.mateocare.ui.feature.weatherForecast.presentation.state.DailyAdviceState
import com.practicum.mateocare.ui.feature.weatherForecast.presentation.state.WeatherForecastScreenUiState
import com.practicum.mateocare.ui.feature.weatherForecast.presentation.state.WeatherForecastState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class WeatherForecastScreenViewModel(
    private val locationInteractor: LocationInteractor,
    private val getWeatherForecastInteractor: GetWeatherForecastInteractor,

    private val profileInteractor: ProfileInteractor,
    private val dailyAdviceInteractor: DailyAdviceInteractor,

    private val forecastMapper: ForecastUiMapper
) : ViewModel() {
    private val _uiState = MutableStateFlow(WeatherForecastScreenUiState())
    val uiState: StateFlow<WeatherForecastScreenUiState> = _uiState.asStateFlow()

    init{
       loadTodayStatus()
    }

    //weather forecast
    fun onPermissionGranted() {
        fetchLocationAndWeather()
    }

    private fun fetchLocationAndWeather() {
        viewModelScope.launch {
            _uiState.update { it.copy(weatherState = WeatherForecastState.Loading(it.weatherState.latitude, it.weatherState.longitude)) }

            locationInteractor.getLocationUpdates(interval = 36000000L)
                .catch { e ->
                    _uiState.update {  it.copy(weatherState = WeatherForecastState.Error("Ошибка GPS")) }
                }
                .collect { location ->
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
                                it.copy(
                                    weatherState = WeatherForecastState.Content(
                                        forecasts = forecastMapper.map(
                                            result.forecasts,
                                            result.forecastUnits,
                                            currentIndexHour
                                        ),
                                        latitude = latitude,
                                        longitude = longitude
                                    ),
                                    weatherForecast = result.forecasts
                                )
                            }
                        }
                        is GetForecastResult.NoResults -> {
                            _uiState.update {
                                it.copy(
                                    weatherState = WeatherForecastState.Empty("Ничего не нашлось")
                                )
                            }
                        }
                        is GetForecastResult.NetworkError -> {
                            _uiState.update {
                                it.copy(
                                    weatherState = WeatherForecastState.Error("Нет подключения к интернету")
                                )
                            }
                        }
                        is GetForecastResult.ServerError -> {
                            _uiState.update {
                                it.copy(
                                    weatherState = WeatherForecastState.Error("Что-то пошло не так")
                                )
                            }
                        }
                    }
                }
        }
    }

    private fun getCurrentHoutIndex(timeList: List<String>): Int {
        val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
        val now = LocalDateTime.now()

        return timeList.indexOfFirst { timeString ->
            val time = LocalDateTime.parse(timeString, formatter)
            time.hour == now.hour && time.dayOfMonth == now.dayOfMonth
        }
    }

    //daily advice
    fun loadTodayStatus() {
        viewModelScope.launch {
            try {
                val user = requireUser() ?: return@launch

                //проверяем наличие сегодняшнего статуса
                val todayStatus = dailyAdviceInteractor.getTodayStatus(user.userId)

                if (todayStatus != null) {
                    _uiState.update {
                        it.copy(
                            dailyAdviceStatus = DailyAdviceState.Content(todayStatus)
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            dailyAdviceStatus = DailyAdviceState.WaitingSurvey
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        dailyAdviceStatus = DailyAdviceState.Error("Ошибка авторизации пользователя")
                    )
                }
            }
        }
    }

    fun loadDailyAdviceAndStatus(
        weatherForecast: WeatherForecast? = null,
        answers: String? = null
    ) {
        _uiState.update {
            it.copy(
                dailyAdviceStatus = DailyAdviceState.Loading
            )
        }
        viewModelScope.launch {
            try {
                val user = requireUser() ?: return@launch

                //ошибка если нет данных для генерации
                if(weatherForecast == null || answers == null) {
                    _uiState.update {
                        it.copy(
                            dailyAdviceStatus = DailyAdviceState.Error(
                                "Недостаточно данных"
                            )
                        )
                    }

                    return@launch
                }

                //генерируем совет и статус
                val generateResult = dailyAdviceInteractor.generateDailyAdvice(user.userId, weatherForecast, answers)
                when(generateResult) {
                    is GenerateAdviceResult.Success -> {
                        _uiState.update {
                            it.copy(
                                dailyAdviceStatus = DailyAdviceState.Content(
                                    userDailyStatus = generateResult.dailyStatus
                                )
                            )
                        }
                    }
                    is GenerateAdviceResult.GenerateError -> {
                        _uiState.update {
                            it.copy(
                                dailyAdviceStatus = DailyAdviceState.Error(
                                    message = "Ошибка генерации"
                                )
                            )
                        }
                    }
                    is GenerateAdviceResult.NoUserDataError -> {
                        _uiState.update {
                            it.copy(
                                dailyAdviceStatus = DailyAdviceState.Error(
                                    message = "Ошибка загрузки данных пользователя"
                                )
                            )
                        }
                    }
                }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        dailyAdviceStatus = DailyAdviceState.Error(
                            "Ошибка сервера"
                        )
                    )
                }
            }
        }
    }

    private suspend fun requireUser(): User? {
        val user = profileInteractor.getCurrentUser()
        if(user == null) {
            _uiState.update {
                it.copy(
                    dailyAdviceStatus = DailyAdviceState.Unauthorized
                )
            }
        }

        return user
    }
}