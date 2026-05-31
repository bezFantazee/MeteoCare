package com.practicum.mateocare.ui.feature.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.mateocare.ai.domain.AiInteractor
import com.practicum.mateocare.ai.domain.models.GetAiResult
import com.practicum.mateocare.profile.domain.firebase.ProfileInteractor
import com.practicum.mateocare.profile.domain.models.HealthProfile
import com.practicum.mateocare.profile.domain.models.MeteoStatus
import com.practicum.mateocare.profile.domain.models.UserDailyStatus
import com.practicum.mateocare.ui.feature.profile.presentation.models.MeteoStatusUi
import com.practicum.mateocare.ui.feature.profile.presentation.models.ProfileUi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.internal.decodeToSequenceByReader

class ProfileViewModel(
    private val profileInteractor: ProfileInteractor,
    private val aiInteractor: AiInteractor
) : ViewModel() {
    //статус авторизации и базовый профиль пользователя
    private val _authState = MutableStateFlow<ProfileScreenUiState>(ProfileScreenUiState.Loading)
    //локальное состояние генерации ai
    private val _aiSubState = MutableStateFlow<MeteoStatusState?>(null)
    //история здоровья
    @OptIn(ExperimentalCoroutinesApi::class)
    private val weeklyHistoryFlow: Flow<List<UserDailyStatus>> = _authState
        .flatMapLatest { state ->
            if (state is ProfileScreenUiState.Authorized) {
                val sevenDaysAgo = System.currentTimeMillis() - (7 * 24 * 60 * 60 * 1000)
                profileInteractor.observeUserWeeklyHealth(state.profile.userId, sevenDaysAgo)
                    .catch { emit(emptyList()) }
            } else {
                flowOf(emptyList())
            }
        }
    //единый стейт для compose
    val uiState: StateFlow<ProfileScreenUiState> = combine(
        _authState,
        _aiSubState,
        weeklyHistoryFlow
    ) { auth, aiState, history ->
        if(auth is ProfileScreenUiState.Authorized) {
            val finalMeteoStatus = when {
                aiState != null -> aiState
                auth.meteoStatus is MeteoStatusState.Content -> {
                    auth.meteoStatus.copy(
                        userMeteoStatus = auth.meteoStatus.userMeteoStatus.copy(healthHistory = history)
                    )
                }
                else -> auth.meteoStatus
            }
            auth.copy(meteoStatus = finalMeteoStatus)
        } else {
            auth
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ProfileScreenUiState.Loading
    )

    // job для подписки на недельную историю статусов пользователя
    private var observeHistoryJob: Job? = null

    init{
        initializeUser()
    }

    //user
    private fun initializeUser(){
        viewModelScope.launch {
            val user = profileInteractor.getCurrentUser()

            if (user == null) {
               _authState.value = ProfileScreenUiState.Unauthorized.Initial
                return@launch
            }
            loadAuthorizedUser(
                uid = user.userId,
                email = user.email
            )
        }
    }

    //состояние экрана пользователя
    fun showAuthMenu() {
        _authState.update {
            ProfileScreenUiState.Unauthorized.Initial
        }
    }

    fun showLoginScreen() {
        _authState.update {
            ProfileScreenUiState.Unauthorized.LoginMode
        }
    }

    fun showRegisterScreen() {
        _authState.update {
            ProfileScreenUiState.Unauthorized.RegisterMode
        }
    }

    //функции для работы с сессией пользователя
    fun logout() {
        _authState.value = ProfileScreenUiState.Loading

        viewModelScope.launch {
            profileInteractor.logout()

            _authState.value = ProfileScreenUiState.Unauthorized.Initial
        }
    }

    fun registerUser(email: String, password: String, age: Int, gender: String) {
        _authState.value = ProfileScreenUiState.Loading

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
                _authState.value = ProfileScreenUiState.Error("Не удалось зарегистрировать пользователя")
            }
        }
    }

    fun loginUser(email: String, password: String) {
        _authState.value = ProfileScreenUiState.Loading
        viewModelScope.launch {
            try {
                val uid = profileInteractor.loginUser(email, password)

                loadAuthorizedUser(
                    uid = uid,
                    email = email
                )
            } catch(e: Exception) {
                _authState.value = ProfileScreenUiState.Error("Ошибка авторизации пользователя")
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
        val userInf = getUserInfo(uid)

        if (userInf == null) {
            _authState.value =
                ProfileScreenUiState.Error("Ошибка получения данных пользователя")
            return
        }

        _authState.value = buildAuthorizedState(
            uid = uid,
            email = email,
            userInf = userInf
        )
    }
    private suspend fun getUserInfo(uid: String): HealthProfile? {
        try {
            return profileInteractor.getHealthProfile(uid)
        } catch(e: Exception) {
            return null
        }
    }
    private fun buildAuthorizedState(
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
    //ai
    fun generateMeteoStatus(answers: String) {
        val currentState = _authState.value as? ProfileScreenUiState.Authorized ?: return

        _aiSubState.value = MeteoStatusState.Loading

        viewModelScope.launch {
            val result = aiInteractor.getMeteoStatus(currentState.profile.age, answers)
            when(result){
                is GetAiResult.Success -> {
                    profileInteractor.updateUserInf(
                        currentState.profile.userId,
                        result.data.meteoStatus,
                        result.data.advice)

                    _authState.update { state ->
                        if(state is ProfileScreenUiState.Authorized) {
                            state.copy(
                                meteoStatus = MeteoStatusState.Content(
                                    MeteoStatusUi(
                                        result.data.meteoStatus,
                                        result.data.advice,
                                        null
                                    )
                                )
                            )
                        } else state
                    }
                    _aiSubState.value = null
                }
                else -> {
                    _aiSubState.value = MeteoStatusState.Error("Не удалось загрузить совет")
                }
            }
        }
    }
}