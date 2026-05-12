package com.practicum.mateocare.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.mateocare.ai.domain.AiInteractor
import com.practicum.mateocare.ai.domain.models.GetAiResult
import com.practicum.mateocare.ai.prompts.model.StatusInput
import com.practicum.mateocare.forecast.domain.models.WeatherForecast
import com.practicum.mateocare.profile.domain.models.MeteoStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AiViewModel(
    val aiInteractor: AiInteractor
) : ViewModel(){
    private val _uiState = MutableStateFlow<AiState>(AiState.Initial)
    val uiState: StateFlow<AiState> = _uiState.asStateFlow()

    fun getStatus(age: Int, answers: String) {
        _uiState.value = AiState.Loading

        viewModelScope.launch {
            aiInteractor.getMeteoStatus(age, answers)
                .collect { result ->
                    when(result){
                        is GetAiResult.Success -> {
                            _uiState.update { AiState.Content(meteoStatus = result.data.meteoStatus, advice = result.data.advice) }
                        }
                        else -> {
                            _uiState.update { AiState.Error(message="Не удалось загрузить совет") }
                        }
                    }
                }
        }
    }

    fun getAdvice(age: Int, meteoStatus: MeteoStatus, weatherForecast: WeatherForecast, answers: String) {
        _uiState.value = AiState.Loading

        viewModelScope.launch {
            aiInteractor.getAdvice(age, meteoStatus, weatherForecast, answers)
                .collect { result ->
                    when(result) {
                        is GetAiResult.Success -> {
                            _uiState.update { AiState.Content(meteoStatus = result.data.meteoStatus, advice = result.data.advice) }
                        }
                        else -> {
                            _uiState.update { AiState.Error(message="Не удалось загрузить совет") }
                        }
                    }

                }
        }
    }
}