package com.practicum.mateocare.profile.data.firebase

import com.google.firebase.auth.FirebaseUser

interface AuthClient {
    suspend fun register(email: String, password: String): String
    suspend fun login(email: String, password: String): String
    suspend fun logout()
    fun getCurrentUser(): FirebaseUser?
}