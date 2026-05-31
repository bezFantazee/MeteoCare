package com.practicum.mateocare.profile.domain.firebase

import android.util.Log
import com.practicum.mateocare.profile.data.firebase.toMap
import com.practicum.mateocare.profile.domain.models.HealthProfile
import com.practicum.mateocare.profile.domain.models.Identifiable
import com.practicum.mateocare.profile.domain.models.MeteoStatus
import com.practicum.mateocare.profile.domain.models.User
import com.practicum.mateocare.profile.domain.models.UserDailyStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProfileInteractorImpl(
    private val repository: ProfileRepository
) : ProfileInteractor {

    //вспомогательные функции
    private suspend fun <T : Identifiable> saveUserInf(
        userId: String,
        collectionName: String,
        data: T,
        documentId: String?
    ) {
        return repository.saveUserInf(userId, collectionName, data, documentId)
    }

    override suspend fun deleteUserInf(
        userId: String,
        collectionName: String,
        documentId: String
    ) {
        return repository.deleteUserInf(userId, collectionName, documentId)
    }

    private suspend fun updateInf(
        userId: String,
        collectionName: String,
        documentId: String,
        updates: Map<String, Any>
    ) {
        return repository.updateUserInf(userId, collectionName, documentId, updates)
    }

    override suspend fun <T : Identifiable> getUserInf(
        userId: String,
        collectionName: String,
        clazz: Class<T>,
        documentId: String
    ): T? {
        return repository.getUserInf(userId, collectionName, clazz, documentId)
    }

    //user
    override suspend fun registerUser(
        email: String,
        password: String,
        age: Int,
        gender: String
    ): String {
        val uid = repository.registerUser(email, password)

        val profileInf =  HealthProfile(
            age = age,
            gender = gender,
            meteoStatus = null,
            mainAdvice = null
        )

        saveUserInf(
            uid,
            "health_profile",
            profileInf,
            "main"
        )
        return uid
    }

    override suspend fun loginUser(email: String, password: String): String {
        return repository.loginUser(email, password)
    }

    override suspend fun logout() {
        repository.logout()
    }

    override suspend fun getCurrentUser(): User? {
        return repository.getCurrentUser()
    }

    //user data
    override suspend fun updateUserInf(
        uid: String,
        meteoStatus: MeteoStatus,
        mainAdvice: String
    ) {
        val updates = mapOf(
            "meteoStatus" to meteoStatus,
            "mainAdvice" to mainAdvice
        )

        updateInf(
            uid,
            "health_profile",
            "main",
            updates
        )
    }
    override suspend fun getHealthProfile(userId: String): HealthProfile? {
        return repository.getUserInf(
            userId = userId,
            collectionName = "health_profile",
            clazz = HealthProfile::class.java,
            documentId = "main"
        )
    }

    //meteo status
    override suspend fun getUserDailyStatus(userId: String, date: String): UserDailyStatus? {
        Log.d("d231", date)
        return repository.getUserInf(
            userId = userId,
            collectionName = "daily_status",
            clazz = UserDailyStatus::class.java,
            documentId = date
        )
    }

    override suspend fun saveUserDailyStatus(
        userId: String,
        dailyStatus: UserDailyStatus
    ) {
        repository.saveUserInf(
            userId = userId,
            collectionName = "daily_status",
            data = dailyStatus,
            documentId = dailyStatus.id
        )
    }

    override fun observeUserWeeklyStatuses(userID: String): Flow<List<UserDailyStatus>?> {
        return repository.observeAllInf(
            userId = userID,
            collectionName = "daily_status",
            clazz = UserDailyStatus::class.java
        ) .map { allStatuses ->
            if(allStatuses.isNullOrEmpty()) {
                emptyList()
            } else {
                val sevenDaysAgo = System.currentTimeMillis() - (7 * 24 * 60 * 60 * 1000)

                allStatuses
                    .filter { it.timestamp >= sevenDaysAgo }
                    .sortedByDescending { it.timestamp }
            }
        }
    }
}