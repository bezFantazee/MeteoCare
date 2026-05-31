package com.practicum.mateocare.profile.data.firebase

import com.google.firebase.auth.FirebaseUser
import com.practicum.mateocare.profile.domain.models.Identifiable
import com.practicum.mateocare.profile.domain.models.User
import kotlinx.coroutines.flow.Flow

interface FirebaseClient {

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

    //регистрация и авторизация пользователей
    suspend fun register(email: String, password: String): String
    suspend fun login(email: String, password: String): String
    suspend fun logout()

    suspend fun getCurrentUser(): FirebaseUser?
}