package com.practicum.mateocare.ui.profile

import com.practicum.mateocare.profile.domain.models.MeteoStatus

sealed interface ProfileState {
    object Loading : ProfileState
    sealed class UnRegisterUser : ProfileState {
        object Initial: UnRegisterUser()
        object LoginMode: UnRegisterUser()
        object RegisterMode: UnRegisterUser()
    }
    class RegisterUser(
        val userId: String,
        val email: String,
        val age: Int,
        val gender: String,
        val meteoStatus: MeteoStatus? = null,
        val mainAdvice: String? = null,
        ) : ProfileState
    class Error(val message: String) : ProfileState
}