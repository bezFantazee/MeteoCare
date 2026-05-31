package com.practicum.mateocare.ui.feature.profile.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.mateocare.pushMessages.domain.NotificationInteractor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NotificationViewModel(
    private val interactor: NotificationInteractor
) : ViewModel() {

    private val _enabled =
        MutableStateFlow(false)

    val enabled: StateFlow<Boolean> = _enabled

    fun enable(hour: Int, minute: Int) {
        viewModelScope.launch {

            interactor.enableNotifications(hour, minute)

            _enabled.value = true
        }
    }

    fun disable() {

        viewModelScope.launch {

            interactor.disableNotifications()

            _enabled.value = false
        }
    }

    fun load() {

        viewModelScope.launch {

            val settings = interactor.getSettings()

            _enabled.value = settings.enabled
        }
    }
}