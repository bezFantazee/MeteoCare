package com.practicum.mateocare.profile.domain.firebase

import com.practicum.mateocare.profile.domain.firebase.ProfileRepository
import com.practicum.mateocare.profile.domain.models.Identifiable
import com.practicum.mateocare.profile.domain.models.User

class ProfileInteractorImpl(
    private val repository: ProfileRepository
) : ProfileInteractor {
    override suspend fun <T : Identifiable> saveUserInf(
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

    override suspend fun updateUserInf(
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

    override suspend fun <T : Identifiable> getAllInf(
        userId: String,
        collectionName: String,
        clazz: Class<T>
    ): List<T>? {
        return repository.getAllInf(userId, collectionName, clazz)
    }

    override suspend fun registerUser(
        email: String,
        password: String
    ): String {
        return repository.registerUser(email, password)
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
}