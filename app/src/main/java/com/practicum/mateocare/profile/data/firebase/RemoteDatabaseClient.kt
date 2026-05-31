package com.practicum.mateocare.profile.data.firebase

import com.practicum.mateocare.profile.domain.models.Identifiable
import kotlinx.coroutines.flow.Flow

interface RemoteDatabaseClient {

    suspend fun <T : Identifiable> save(
        userId: String,
        collectionName: String,
        data: T,
        documentId: String? = null
    )

    suspend fun delete(
        userId: String,
        collectionName: String,
        documentId: String
    )

    suspend fun update(
        userId: String,
        collectionName: String,
        documentId: String,
        updates: Map<String, Any>
    )

    suspend fun <T : Identifiable> getDocumentSafe(
        userId: String,
        collectionName: String,
        clazz: Class<T>,
        documentId: String
    ) : T?

    fun <T : Identifiable> observeDocumentsSafe(
        userId: String,
        collectionName: String,
        clazz: Class<T>
    ): Flow<List<T>?>
}