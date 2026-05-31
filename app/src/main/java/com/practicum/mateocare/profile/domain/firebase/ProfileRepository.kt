package com.practicum.mateocare.profile.domain.firebase

import com.practicum.mateocare.profile.domain.models.HealthProfile
import com.practicum.mateocare.profile.domain.models.Identifiable
import com.practicum.mateocare.profile.domain.models.User
import com.practicum.mateocare.profile.domain.models.UserDailyStatus
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    //health profile
    suspend fun getHealthProfile(userId: String): HealthProfile?
    suspend fun saveHealthProfile(userId: String, profile: HealthProfile)
    suspend fun updateHealthProfile(userId: String, updates: Map<String, Any>)

    //daily statuses
    suspend fun getDailyStatus(userId: String, date: String): UserDailyStatus?
    suspend fun saveDailyStatus(userId: String, status: UserDailyStatus)
    fun observeDailyStatuses(userId: String): Flow<List<UserDailyStatus>>

    //регстрация и авторизация пользователей
    suspend fun registerUser(email: String, password: String): String
    suspend fun loginUser(email: String, password: String): String
    suspend fun logout()
    suspend fun getCurrentUser(): User?
}