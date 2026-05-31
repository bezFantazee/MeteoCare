package com.practicum.mateocare.profile.domain.firebase

import android.util.Log
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
    //user authorization
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

        repository.saveHealthProfile(uid, profileInf)
        return uid
    }

    override suspend fun loginUser(email: String, password: String): String =
        repository.loginUser(email, password)

    override suspend fun logout() = repository.logout()

    override suspend fun getCurrentUser(): User? =
        repository.getCurrentUser()

    //health profile
    override suspend fun updateUserInf(
        uid: String,
        meteoStatus: MeteoStatus,
        mainAdvice: String
    ) {
        val updates = mapOf(
            "meteoStatus" to meteoStatus,
            "mainAdvice" to mainAdvice
        )

        repository.updateHealthProfile(uid, updates)
    }
    override suspend fun getHealthProfile(userId: String): HealthProfile? {
        return repository.getHealthProfile(userId)
    }

    //daily status
    override suspend fun getUserDailyStatus(userId: String, date: String): UserDailyStatus? {
        return repository.getDailyStatus(userId, date)
    }

    override suspend fun saveUserDailyStatus(
        userId: String,
        dailyStatus: UserDailyStatus
    ) {
        repository.saveDailyStatus(userId, dailyStatus)
    }

    override fun observeUserWeeklyHealth(userID: String, filterSinceTimestamp: Long): Flow<List<UserDailyStatus>> {
        return repository.observeDailyStatuses(userID)
            .map { allStatuses ->

                allStatuses
                    .filter { it.timestamp >= filterSinceTimestamp }
                    .sortedByDescending { it.timestamp }
            }
    }
}