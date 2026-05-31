package com.practicum.mateocare.ui.feature.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.mateocare.ai.domain.AiInteractor
import com.practicum.mateocare.ai.domain.models.GetAiResult
import com.practicum.mateocare.profile.domain.firebase.ProfileInteractor
import com.practicum.mateocare.profile.domain.models.HealthProfile
import com.practicum.mateocare.profile.domain.models.MeteoStatus
import com.practicum.mateocare.ui.feature.profile.presentation.models.MeteoStatusUi
import com.practicum.mateocare.ui.feature.profile.presentation.models.ProfileUi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val profileInteractor: ProfileInteractor,
    private val aiInteractor: AiInteractor
) : ViewModel() {
    private val _uiState = MutableStateFlow<ProfileScreenUiState>(ProfileScreenUiState.Loading)
    val uiState: StateFlow<ProfileScreenUiState> = _uiState.asStateFlow()

    // job для подписки на недельную историю статусов пользователя
    private var observeHistoryJob: Job? = null

    init{
        requireUser()
    }

    //user
    private fun requireUser(){
        viewModelScope.launch {
            val user = profileInteractor.getCurrentUser()

            if (user == null) {
                _uiState.value =
                    ProfileScreenUiState.Unauthorized.Initial
                return@launch
            }

            loadAuthorizedUser(
                uid = user.userId,
                email = user.email
            )
        }
    }

    //состояние экрана пользователя
    fun back() {
        _uiState.update {
            ProfileScreenUiState.Unauthorized.Initial
        }
    }

    fun showLogin() {
        _uiState.update {
            ProfileScreenUiState.Unauthorized.LoginMode
        }
    }

    fun showRegister() {
        _uiState.update {
            ProfileScreenUiState.Unauthorized.RegisterMode
        }
    }

    //функции для работы с сессией пользователя
    fun logout() {
        _uiState.update {
            ProfileScreenUiState.Loading
        }

        viewModelScope.launch {
            profileInteractor.logout()

            _uiState.update {
                ProfileScreenUiState.Unauthorized.Initial
            }
        }
    }

    fun registerUser(email: String, password: String, age: Int, gender: String) {
        _uiState.update { ProfileScreenUiState.Loading }

        viewModelScope.launch {
            try {
                val uid = profileInteractor.registerUser(
                    email = email,
                    password = password,
                    age = age,
                    gender = gender
                )
                loadAuthorizedUser(
                    uid = uid,
                    email = email
                )
            } catch(e: Exception) {
                _uiState.update {
                    ProfileScreenUiState.Error("Не удалось зарегистрировать пользователя")
                }
            }
        }
    }

    fun loginUser(email: String, password: String) {
        _uiState.update { ProfileScreenUiState.Loading }
        viewModelScope.launch {
            try {
                val uid = profileInteractor.loginUser(email, password)

                loadAuthorizedUser(
                    uid = uid,
                    email = email
                )
            } catch(e: Exception) {
                _uiState.update { ProfileScreenUiState.Error("Ошибка авторизации пользователя") }
            }
        }
    }

    //сохранение информации в локальное хранилище
    private suspend fun updateMeteoStatus(meteoStatus: MeteoStatus, advice: String) {
        val userId = profileInteractor.getCurrentUser()?.userId ?: return

        profileInteractor.updateUserInf(
            uid = userId,
            meteoStatus = meteoStatus,
            mainAdvice = advice
        )
    }

    //вспомогательные функции для пользователя
    private suspend fun loadAuthorizedUser(
        uid: String,
        email: String
    ) {
        val userInf = getUserInf(uid)

        if (userInf == null) {
            _uiState.value =
                ProfileScreenUiState.Error("Ошибка получения данных пользователя")
            return
        }

        _uiState.value = mapAuthorizedState(
            uid = uid,
            email = email,
            userInf = userInf
        )

        observeStatusHistory()
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
            return null
        }
    }
    private fun mapAuthorizedState(
        uid: String,
        email: String,
        userInf: HealthProfile
    ): ProfileScreenUiState.Authorized {
        val profile = ProfileUi(
            userId = uid,
            email = email,
            age = userInf.age,
            gender = userInf.gender
        )

        val meteoStatusState =
            if(userInf.meteoStatus != null && userInf.mainAdvice != null) {

                MeteoStatusState.Content(
                    MeteoStatusUi(
                        meteoStatus = userInf.meteoStatus,
                        advice = userInf.mainAdvice,
                        healthHistory = null
                    )
                )
            } else {
                MeteoStatusState.Empty
            }

        return ProfileScreenUiState.Authorized(
            profile = profile,
            meteoStatus = meteoStatusState
        )
    }

    fun observeStatusHistory(){
        observeHistoryJob?.cancel()
        observeHistoryJob = viewModelScope.launch {
            val authorizedState =
                _uiState.value as? ProfileScreenUiState.Authorized
                    ?: return@launch

            val userId = authorizedState.profile.userId

            profileInteractor.observeUserWeeklyStatuses(userId)
                .collect { filteredList ->

                    _uiState.update { state ->

                        if(
                            state is ProfileScreenUiState.Authorized &&
                            state.meteoStatus is MeteoStatusState.Content
                        ) {

                            state.copy(
                                meteoStatus = state.meteoStatus.copy(
                                    userMeteoStatus = state.meteoStatus.userMeteoStatus.copy(
                                        healthHistory = filteredList
                                    )
                                )
                            )

                        } else {
                            state
                        }
                    }
                }
        }
    }

    //ai
    fun getMeteoStatus(answers: String) {
        val currentState = _uiState.value

        if(currentState is ProfileScreenUiState.Authorized) {

            _uiState.update { state ->
                if(state is ProfileScreenUiState.Authorized) {
                    state.copy(
                        meteoStatus = MeteoStatusState.Loading
                    )
                } else {
                    state
                }
            }

            val age = currentState.profile.age

            viewModelScope.launch {

                when(val result = aiInteractor.getMeteoStatus(age, answers)) {

                    is GetAiResult.Success -> {
                        val meteoStatus = MeteoStatusUi(
                            meteoStatus = result.data.meteoStatus,
                            advice = result.data.advice,
                            healthHistory = null
                        )

                        updateMeteoStatus(
                            meteoStatus = result.data.meteoStatus,
                            advice = result.data.advice
                        )

                        _uiState.update { state ->
                            if(state is ProfileScreenUiState.Authorized) {
                                state.copy(
                                    meteoStatus = MeteoStatusState.Content(
                                        meteoStatus
                                    )
                                )
                            } else {
                                state
                            }
                        }
                    }

                    else -> {
                        _uiState.update { state ->
                            if(state is ProfileScreenUiState.Authorized) {
                                state.copy(
                                    meteoStatus = MeteoStatusState.Error(
                                        "Не удалось загрузить совет"
                                    )
                                )
                            } else {
                                state
                            }
                        }
                    }
                }
            }
        }
    }
}