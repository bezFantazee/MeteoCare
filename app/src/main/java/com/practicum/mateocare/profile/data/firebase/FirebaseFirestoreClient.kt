package com.practicum.mateocare.profile.data.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.practicum.mateocare.profile.domain.models.Identifiable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class FirebaseFirestoreClient(
    val db: FirebaseFirestore,
    val mainCollectionName: String
) : RemoteDatabaseClient {
    override suspend fun <T : Identifiable> save(
        userId: String,
        collectionName: String,
        data: T,
        documentId: String?
    ) {
        withContext(Dispatchers.IO) {
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
    }

    override suspend fun delete(
        userId: String,
        collectionName: String,
        documentId: String
    ) {
        withContext(Dispatchers.IO) {
            db.collection(mainCollectionName)
                .document(userId)
                .collection(collectionName)
                .document(documentId)
                .delete()
                .await()
        }
    }

    override suspend fun update(
        userId: String,
        collectionName: String,
        documentId: String,
        updates: Map<String, Any>
    ) {
        withContext(Dispatchers.IO) {
            db.collection(mainCollectionName)
                .document(userId)
                .collection(collectionName)
                .document(documentId)
                .update(updates)
                .await()
        }
    }

    override suspend fun <T : Identifiable> getDocumentSafe(
        userId: String,
        collectionName: String,
        clazz: Class<T>,
        documentId: String
    ): T? = withContext(Dispatchers.IO) {
        val snapshot = db.collection(mainCollectionName)
            .document(userId)
            .collection(collectionName)
            .document(documentId)
            .get()
            .await()

        val item = snapshot.toObject(clazz)
        item?.withId(snapshot.id) as? T
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
                if (error != null) {
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
    }.flowOn(Dispatchers.IO)
}