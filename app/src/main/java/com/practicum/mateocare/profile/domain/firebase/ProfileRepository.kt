package com.practicum.mateocare.profile.domain.firebase

import com.practicum.mateocare.profile.domain.models.Identifiable
import com.practicum.mateocare.profile.domain.models.User

interface ProfileRepository {
    suspend fun <T : Identifiable> saveUserInf(
        userId: String,
        collectionName: String,
        data: T,
        documentId: String? = null
    )
    suspend fun deleteUserInf(
        userId: String,
        collectionName: String,
        documentId: String
    )
    suspend fun updateUserInf(
        userId: String,
        collectionName: String,
        documentId: String,
        updates: Map<String, Any>
    )

    suspend fun <T : Identifiable> getUserInf(
        userId: String,
        collectionName: String,
        clazz: Class<T>,
        documentId: String
    ): T?

    suspend fun <T : Identifiable> getAllInf(
        userId: String,
        collectionName: String,
        clazz: Class<T>
    ): List<T>?

    //регстрация и авторизация пользователей
    suspend fun registerUser(email: String, password: String): String
    suspend fun loginUser(email: String, password: String): String
    suspend fun logout()
    suspend fun getCurrentUser(): User?
}