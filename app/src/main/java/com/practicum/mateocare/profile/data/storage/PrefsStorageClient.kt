package com.practicum.mateocare.profile.data.storage

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.practicum.mateocare.profile.data.storage.StorageClient

class PrefsStorageClient(
    context: Context
) : StorageClient {
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val sharedPreferences = EncryptedSharedPreferences.create(
        context,
        "user_preferences",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    override suspend fun saveUserIds(userId: String, dataId: String) {
        sharedPreferences.edit()
            .putString(USER_ID, userId)
            .putString(DATA_ID, dataId)
            .apply()
    }

    override fun getUserIds(): Pair<String?, String?> {
        return sharedPreferences.getString(USER_ID, null) to
                sharedPreferences.getString(DATA_ID, null)
    }

    private companion object{
        const val USER_ID = "USER_ID"
        const val DATA_ID = "DATA_ID"
    }
}