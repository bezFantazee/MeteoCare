package com.practicum.mateocare.profile.data

import com.practicum.mateocare.profile.data.firebase.AuthClient
import com.practicum.mateocare.profile.data.firebase.RemoteDatabaseClient
import com.practicum.mateocare.profile.domain.firebase.ProfileRepository
import com.practicum.mateocare.profile.domain.models.HealthProfile
import com.practicum.mateocare.profile.domain.models.Identifiable
import com.practicum.mateocare.profile.domain.models.User
import com.practicum.mateocare.profile.domain.models.UserDailyStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProfileRepositoryImpl(
    private val remoteDatabaseClient: RemoteDatabaseClient,
    private val authClient: AuthClient
    ) : ProfileRepository {
    //работа с данными пользователя(remoteDtabaseClient)
    //универсалье функции для чтения и записи
    private suspend fun <T : Identifiable> getDoc(uid: String, coll: String, clazz: Class<T>, docId: String): T? {
        return remoteDatabaseClient.getDocumentSafe(uid, coll, clazz, docId)
    }
    private suspend fun <T : Identifiable> saveDoc(uid: String, coll: String, data: T, docId: String?) {
        remoteDatabaseClient.save(uid, coll, data, docId)
    }
    private suspend fun updateDoc(uid: String, coll: String, docId: String, updates: Map<String, Any>){
        remoteDatabaseClient.update(uid, coll, docId, updates)
    }
    private inline fun <reified T : Identifiable> observeDocs(
        userId: String,
        collectionName: String
    ): Flow<List<T>> {
        return remoteDatabaseClient.observeDocumentsSafe(userId, collectionName, T::class.java)
            .map { it ?: emptyList() }
    }

    //health profile
    override suspend fun getHealthProfile(userId: String): HealthProfile? =
        getDoc(userId, COLLECTION_HEALTH_PROFILE, HealthProfile::class.java, "main")


    override suspend fun saveHealthProfile(userId: String, profile: HealthProfile) =
        saveDoc(userId, COLLECTION_HEALTH_PROFILE, profile, DOCUMENT_MAIN)

    override suspend fun updateHealthProfile(userId: String, updates: Map<String, Any>) {
        updateDoc(userId, COLLECTION_HEALTH_PROFILE, DOCUMENT_MAIN, updates)
    }

    //daily statuses
    override suspend fun getDailyStatus(userId: String, date: String): UserDailyStatus? =
        getDoc(userId, COLLECTION_DAILY_STATUS, UserDailyStatus::class.java, date)

    override suspend fun saveDailyStatus(userId: String, status: UserDailyStatus) =
        saveDoc(userId, COLLECTION_DAILY_STATUS, status, status.id)

    override fun observeDailyStatuses(userId: String): Flow<List<UserDailyStatus>> {
        return observeDocs<UserDailyStatus>(userId, COLLECTION_DAILY_STATUS)
    }

    override suspend fun registerUser(
        email: String,
        password: String
    ): String {
        return authClient.register(email, password)
    }

    override suspend fun loginUser(email: String, password: String): String {
        return authClient.login(email, password)
    }

    override suspend fun logout() {
        return authClient.logout()
    }

    override suspend fun getCurrentUser(): User? {
        val user = authClient.getCurrentUser() ?: return null
        return User(
            user.uid,
            user.email ?: "Не определен"
        )
    }

    companion object{
        private const val COLLECTION_HEALTH_PROFILE = "health_profile"
        private const val COLLECTION_DAILY_STATUS = "daily_status"
        private const val DOCUMENT_MAIN = "main"
    }
}