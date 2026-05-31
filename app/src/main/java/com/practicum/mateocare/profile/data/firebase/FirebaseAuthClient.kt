package com.practicum.mateocare.profile.data.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class FirebaseAuthClient(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) : AuthClient {
    override suspend fun register(email: String, password: String): String =
        withContext(Dispatchers.IO) {
            val result = auth
                .createUserWithEmailAndPassword(email, password)
                .await()
            result.user?.uid
                ?: throw IllegalStateException("User UID is missing after successful registration")
        }

    override suspend fun login(email: String, password: String): String =
        withContext(Dispatchers.IO) {
            val result = auth
                .signInWithEmailAndPassword(email, password)
                .await()
            result.user?.uid
                ?: throw IllegalStateException("User UID is missing after successful registration")
        }

    override suspend fun logout() {
        auth.signOut()
    }

    override fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }
}