package com.arpan.notesapp.firebase

import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

@ExperimentalCoroutinesApi
class FirebaseUtils(
    private var notesCollection : CollectionReference
) {



    fun getAllNotes(uid: String, orderBy: String, type: Query.Direction) : Flow<List<Note>> = callbackFlow {
        val subscription = notesCollection.whereEqualTo("uid", uid).orderBy(orderBy, type).addSnapshotListener { snapshot, _ ->
            if(snapshot == null) {
                return@addSnapshotListener
            }
            try {
                offer(snapshot.toObjects(Note::class.java))
            } catch (e : Exception) {
                Log.d("supremo", e.toString())
            }
        }

        awaitClose{ subscription.remove() }
    }

    suspend fun insertNote(id : String, note: Note) : Boolean {
        return  try {
            notesCollection.document(id).set(note).await()
            true
        } catch (e : java.lang.Exception) {
            false
        }
    }

    suspend fun updateNote(id : String, note: Note) : Boolean {
        return try {
            notesCollection.document(id).update(
                "title", note.title,
                "description", note.description,
                "image", note.image,
                "lastEdited", note.lastEdited
            ).await()
            true
        } catch (e : Exception) {
            false
        }
    }

    suspend fun deleteNote(id: String) : Boolean {
        return try {
            notesCollection.document(id).delete().await()
            true
        } catch (e : Exception) {
            false
        }
    }

}
