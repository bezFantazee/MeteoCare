package com.practicum.mateocare.profile.domain.storage

import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {
    suspend fun saveUserIds(userId: String, dataId: String)
    fun getUserIds(): Flow<Pair<String?, String?>>
}