package com.practicum.mateocare.profile.data.firebase

import com.practicum.mateocare.profile.domain.firebase.ProfileRepository
import com.practicum.mateocare.profile.domain.models.Identifiable
import com.practicum.mateocare.profile.domain.models.User

class ProfileRepositoryImpl(
    private val dbClient: FirebaseClient
) : ProfileRepository {
    override suspend fun <T : Identifiable> saveUserInf(
        userId: String,
        collectionName: String,
        data: T,
        documentId: String?
    ) {
        dbClient.save(userId, collectionName, data, documentId)
    }

    override suspend fun deleteUserInf(
        userId: String,
        collectionName: String,
        documentId: String
    ) {
        dbClient.delete(userId, collectionName, documentId)
    }

    override suspend fun updateUserInf(
        userId: String,
        collectionName: String,
        documentId: String,
        updates: Map<String, Any>
    ) {
        dbClient.update(userId, collectionName, documentId, updates)
    }

    override suspend fun <T : Identifiable> getUserInf(
        userId: String,
        collectionName: String,
        clazz: Class<T>,
        documentId: String
    ): T? {
        return dbClient.getDocumentSafe(userId, collectionName, clazz, documentId)
    }

    override suspend fun <T : Identifiable> getAllInf(
        userId: String,
        collectionName: String,
        clazz: Class<T>
    ): List<T>? {
        return dbClient.getDocumentsSafe(userId, collectionName, clazz)
    }

    override suspend fun registerUser(
        email: String,
        password: String
    ): String {
        return dbClient.register(email, password)
    }

    override suspend fun loginUser(email: String, password: String): String {
        return dbClient.login(email, password)
    }

    override suspend fun logout() {
        return dbClient.logout()
    }

    override suspend fun getCurrentUser(): User? {
        val user = dbClient.getCurrentUser() ?: return null
        return User(
            user.uid,
            user.email ?: "Не определен"
        )
    }
}