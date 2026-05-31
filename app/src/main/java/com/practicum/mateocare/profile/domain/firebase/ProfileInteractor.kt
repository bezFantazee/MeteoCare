package com.practicum.mateocare.profile.domain.firebase

import com.practicum.mateocare.profile.domain.models.HealthProfile
import com.practicum.mateocare.profile.domain.models.Identifiable
import com.practicum.mateocare.profile.domain.models.MeteoStatus
import com.practicum.mateocare.profile.domain.models.User
import com.practicum.mateocare.profile.domain.models.UserDailyStatus
import kotlinx.coroutines.flow.Flow

interface ProfileInteractor {
    suspend fun deleteUserInf(
        userId: String,
        collectionName: String,
        documentId: String
    )

    suspend fun <T : Identifiable> getUserInf(
        userId: String,
        collectionName: String,
        clazz: Class<T>,
        documentId: String
    ): T?

    //регистрация и авторизация пользователя
    suspend fun registerUser(
        email: String,
        password: String,
        age: Int,
        gender: String
    ): String
    suspend fun loginUser(email: String, password: String): String
    suspend fun logout()
    suspend fun getCurrentUser(): User?

    //user data
    suspend fun updateUserInf(
        uid: String,
        meteoStatus: MeteoStatus,
        mainAdvice: String)

    //обновленные функции
    suspend fun getHealthProfile(
        userId: String
    ): HealthProfile?

    suspend fun getUserDailyStatus(
        userId: String,
        date: String
    ): UserDailyStatus?

    suspend fun saveUserDailyStatus(
        userId: String,
        dailyStatus: UserDailyStatus
    )

    fun observeUserWeeklyStatuses(userID: String): Flow<List<UserDailyStatus>?>
}