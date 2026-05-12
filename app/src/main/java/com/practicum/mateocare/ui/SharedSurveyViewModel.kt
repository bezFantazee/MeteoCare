package com.practicum.mateocare.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.mateocare.profile.domain.firebase.ProfileInteractor
import com.practicum.mateocare.profile.domain.models.UserDailyStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SharedSurveyViewModel(
    private val interactor: ProfileInteractor
) : ViewModel() {
    private val _sharedStatus = MutableStateFlow<String?>(null)
    val sharedStatus = _sharedStatus.asStateFlow()

    private val _weeklyStatuses = MutableStateFlow<List<UserDailyStatus>>(emptyList())
    val weeklyStatuses = _weeklyStatuses.asStateFlow()

    private val _isSurveyDoneToday = MutableStateFlow(false)
    val isSurveyDoneToday = _isSurveyDoneToday.asStateFlow()

    fun updateStatus(newStatus: String) {
        _sharedStatus.value = newStatus
    }

    fun saveDailyStatus(userId: String, advice: String) {
        val currentStatus = _sharedStatus.value ?: return

        val dateId = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

        val dailyData = UserDailyStatus(
            id = dateId,
            status = currentStatus,
            advice = advice
        )

        viewModelScope.launch {
            try {
                interactor.saveUserInf(
                    userId = userId,
                    collectionName = "daily_stats",
                    data = dailyData,
                    documentId = dateId
                )
            } catch (e: Exception) {
               Log.d("erro!!2", e.message.toString())
            }
        }
    }

    fun fetchWeeklyStatuses(userId: String) {
        viewModelScope.launch {
            try {
                val allStatuses = interactor.getAllInf(
                    userId = userId,
                    collectionName = "daily_stats",
                    clazz = UserDailyStatus::class.java
                )

                if (allStatuses != null) {
                    // Вычисляем время 7 дней назад
                    val sevenDaysAgo = System.currentTimeMillis() - (7 * 24 * 60 * 60 * 1000)

                    // Фильтруем и сортируем по дате (от новых к старым)
                    val filteredList = allStatuses
                        .filter { it.timestamp >= sevenDaysAgo }
                        .sortedByDescending { it.timestamp }

                    _weeklyStatuses.value = filteredList
                }
            } catch (e: Exception) {
                // Обработка ошибки
            }
        }
    }

    fun checkTodayStatus(userId: String) {
        val dateId = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        Log.d("daily", dateId)

        viewModelScope.launch {
            try {
                val result = interactor.getUserInf(
                    userId = userId,
                    collectionName = "daily_stats",
                    clazz = UserDailyStatus::class.java,
                    documentId = dateId
                )
                Log.d("daily", result.toString())
                // Если документ нашелся (не null) — значит опрос пройден
                _isSurveyDoneToday.value = result != null
            } catch (e: Exception) {
                _isSurveyDoneToday.value = false
                Log.d("daily", e.message.toString())
            }
        }
    }

    private val _todayStatus = MutableStateFlow<UserDailyStatus?>(null)
    val todayStatus = _todayStatus.asStateFlow()

    fun fetchTodayStatus(userId: String) {
        val dateId = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

        viewModelScope.launch {
            try {
                val result = interactor.getUserInf(
                    userId = userId,
                    collectionName = "daily_stats",
                    clazz = UserDailyStatus::class.java,
                    documentId = dateId
                )
                _todayStatus.value = result
            } catch (e: Exception) {
                _todayStatus.value = null
            }
        }
    }
}