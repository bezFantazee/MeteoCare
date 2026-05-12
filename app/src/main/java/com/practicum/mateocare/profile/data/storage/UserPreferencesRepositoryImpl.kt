package com.practicum.mateocare.profile.data.storage

import com.practicum.mateocare.profile.domain.storage.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserPreferencesRepositoryImpl(
    private val storageClient: StorageClient
) : UserPreferencesRepository {
    override suspend fun saveUserIds(userId: String, dataId: String) {
        storageClient.saveUserIds(userId, dataId)
    }

    override fun getUserIds(): Flow<Pair<String?, String?>> = flow {
        emit(
            storageClient.getUserIds()
        )
    }
}