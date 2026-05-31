package com.practicum.mateocare.profile.data.firebase

import androidx.compose.runtime.snapshotFlow
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestoreSettings
import com.practicum.mateocare.profile.domain.models.Identifiable
import com.practicum.mateocare.profile.domain.models.User
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirestoreClient(
    val db: FirebaseFirestore,
    val mainCollectionName: String
) : FirebaseClient {
    override suspend fun <T : Identifiable> save(
        userId: String,
        collectionName: String,
        data: T,
        documentId: String?
    ) {
        val collectionRef = db.collection(mainCollectionName)
            .document(userId)
            .collection(collectionName)

        val ref = if (documentId != null) {
            collectionRef.document(documentId)
        } else {
            collectionRef.document()
        }

        val dataWithId = data.withId(ref.id)

        ref.set(dataWithId).await()
    }

    override suspend fun delete(
        userId: String,
        collectionName: String,
        documentId: String
    ) {
        db.collection(mainCollectionName)
            .document(userId)
            .collection(collectionName)
            .document(documentId)
            .delete()
            .await()
    }

    override suspend fun update(
        userId: String,
        collectionName: String,
        documentId: String,
        updates: Map<String, Any>
    ) {
        db.collection(mainCollectionName)
            .document(userId)
            .collection(collectionName)
            .document(documentId)
            .update(updates)
            .await()
    }

    override suspend fun <T : Identifiable> getDocumentSafe(
        userId: String,
        collectionName: String,
        clazz: Class<T>,
        documentId: String
    ): T? {
        return try {
            val snapshot = db.collection(mainCollectionName)
                .document(userId)
                .collection(collectionName)
                .document(documentId)
                .get()
                .await()

            val item = snapshot.toObject(clazz)

            item?.withId(snapshot.id) as? T
        } catch (e: Exception) {
            throw Exception(e.message)
        }
    }

    override fun <T : Identifiable> observeDocumentsSafe(
        userId: String,
        collectionName: String,
        clazz: Class<T>
    ): Flow<List<T>> = callbackFlow {
        val listener = db
            .collection(mainCollectionName)
            .document(userId)
            .collection(collectionName)
            .addSnapshotListener { snapshot, error ->
                if(error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                val statuses = snapshot?.documents
                    ?.mapNotNull {
                        it.toObject(
                            clazz
                        )
                    }
                    ?: emptyList()

                trySend(statuses)
            }

        awaitClose {
            listener.remove()
        }
    }

    override suspend fun register(email: String, password: String): String {
        val result = FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(email, password)
            .await()
        return result.user?.uid ?: throw Exception("User is null")
    }

    override suspend fun login(email: String, password: String): String {
        val result = FirebaseAuth.getInstance()
            .signInWithEmailAndPassword(email, password)
            .await()
        return result.user?.uid ?: throw Exception("User is null")
    }

    override suspend fun logout() {
        FirebaseAuth.getInstance().signOut()
    }

    override suspend fun getCurrentUser(): FirebaseUser? {
        return FirebaseAuth.getInstance().currentUser
    }
}