package com.practicum.mateocare.profile.data.storage

interface StorageClient {
    suspend fun saveUserIds(userId: String, dataId: String)
    fun getUserIds(): Pair<String?, String?>
}