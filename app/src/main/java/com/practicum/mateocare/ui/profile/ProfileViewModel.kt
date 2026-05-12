package com.practicum.mateocare.ui.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.mateocare.profile.domain.firebase.ProfileInteractor
import com.practicum.mateocare.profile.domain.models.HealthProfile
import com.practicum.mateocare.profile.domain.models.MeteoStatus
import com.practicum.mateocare.profile.domain.models.User
import com.practicum.mateocare.profile.domain.storage.UserPreferencesRepository
import com.practicum.mateocare.ui.forecast.WeatherForecastState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val profileInteractor: ProfileInteractor
) : ViewModel() {
    private val _uiState = MutableStateFlow<ProfileState>(ProfileState.Loading)
    val uiState: StateFlow<ProfileState> = _uiState.asStateFlow()

    init{
        viewModelScope.launch {
            val user = profileInteractor.getCurrentUser()
            Log.d("firestore", user.toString())
            if (user != null) {
                val userInf = getUserInf(user.userId)
                if(userInf == null) {
                    _uiState.update { ProfileState.Error("Ошибка получения данных пользователя") }
                    return@launch
                }
                _uiState.update { ProfileState.RegisterUser(user.userId, user.email, userInf.age, userInf.gender,
                    userInf.meteoStatus, userInf.mainAdvice
                ) }
            } else {
                _uiState.update { ProfileState.UnRegisterUser.Initial }
            }
        }
    }

    fun back() {
        _uiState.value = ProfileState.UnRegisterUser.Initial
    }

    fun logout() {
        viewModelScope.launch {
            profileInteractor.logout()
        }
        _uiState.value = ProfileState.UnRegisterUser.Initial
    }

    fun onGetMeteoStatus(meteoStatus: MeteoStatus, advice: String) {
        viewModelScope.launch {
           updateUserInf(meteoStatus, advice)
        }
    }

    private suspend fun updateUserInf(meteoStatus: MeteoStatus, advice: String) {
        val userId = profileInteractor.getCurrentUser()?.userId ?: return

        val updates = mapOf(
            "meteoStatus" to meteoStatus,
            "mainAdvice" to advice
        )
        profileInteractor.updateUserInf(
            userId,
            "health_profile",
            "main",
            updates
        )
    }

    fun showLogin() {
        _uiState.update { ProfileState.UnRegisterUser.LoginMode }
    }

    fun showRegister() {
        _uiState.update { ProfileState.UnRegisterUser.RegisterMode }
    }

    fun registerUser(email: String, password: String, age: Int, gender: String) {
        _uiState.update { ProfileState.Loading }

        viewModelScope.launch {
            try {
                val uid = profileInteractor.registerUser(email, password)
                saveUserData(uid, age, gender)
                _uiState.update { ProfileState.RegisterUser(uid, email, age, gender) }
            } catch(e: Exception) {
                _uiState.update { ProfileState.Error("Не удалось зарегистрировать пользователя") }
                Log.e("firestore", e.message.toString())
            }
        }
    }

    private suspend fun saveUserData(uid: String, age: Int, gender: String, meteoStatus: MeteoStatus? = null, mainAdvice: String? = null) {
        val profileInf =  HealthProfile(
            age = age,
            gender = gender,
            meteoStatus = meteoStatus,
            mainAdvice = mainAdvice
        )

        profileInteractor.saveUserInf(
                uid,
                "health_profile",
                profileInf,
            "main"
            )
    }

    fun loginUser(email: String, password: String) {
        Log.d("firestore", "Login")
        viewModelScope.launch {
            try {
                val uid = profileInteractor.loginUser(email, password)
                Log.d("firestore", uid)

                val userInf = getUserInf(uid)
                Log.d("firestore", userInf.toString())

                if (userInf != null) {
                    _uiState.update { ProfileState.RegisterUser(
                        uid,
                        email,
                        userInf.age,
                        userInf.gender,
                        userInf.meteoStatus,
                        userInf.mainAdvice
                    ) }
                }  else {
                    _uiState.update { ProfileState.Error("Ошибка авторизации пользователя") }
                }
            } catch(e: Exception) {
                _uiState.update { ProfileState.Error("Ошибка авторизации пользователя") }
                Log.d("firestore", e.message.toString())
            }
        }
    }

    private suspend fun getUserInf(uid: String): HealthProfile? {
        try {
            return profileInteractor.getUserInf(
                userId = uid,
                collectionName = "health_profile",
                clazz = HealthProfile::class.java,
                documentId = "main"
            )
        } catch(e: Exception) {
            _uiState.update { ProfileState.Error("Ошибка авторизации пользователя") }
            Log.d("firestore", e.message.toString())
            return null
        }
    }

}