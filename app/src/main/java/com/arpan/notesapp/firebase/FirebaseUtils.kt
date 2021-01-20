package com.arpan.notesapp.firebase

import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

class FirebaseUtils(
    private var notesCollection : CollectionReference
) {



    suspend fun getAllNotes() : List<Note>? {
        return try {
            notesCollection.get().await().toObjects(Note::class.java)
        } catch (e : Exception) {
            emptyList()
        }
    }

    suspend fun getAllNotesSortedByDate(uid: String) : List<Note>? {
        return try {
            notesCollection.whereEqualTo("uid", uid).orderBy("dateCreated").get().await().toObjects(Note::class.java)
        } catch (e : Exception) {
            emptyList()
        }
    }

    suspend fun getAllNotesSortedByTitle(uid: String) : List<Note>? {
        return try {
            notesCollection.whereEqualTo("uid", uid).orderBy("title").get().await().toObjects(Note::class.java)
        } catch (e : Exception) {
            emptyList()
        }
    }

    suspend fun getAllNotesSortedByDescription(uid: String) : List<Note>? {
        return try {
            notesCollection.whereEqualTo("uid", uid).orderBy("description").get().await().toObjects(Note::class.java)
        } catch (e : Exception) {
            emptyList()
        }
    }

    suspend fun getAllNotesSortedByLastEdited(uid: String) : List<Note>? {
        return try {
            notesCollection.whereEqualTo("uid", uid).orderBy("lastEdited", Query.Direction.DESCENDING).get().await().toObjects(Note::class.java)
        } catch (e : Exception) {
            Log.d("supremo", e.toString())
            emptyList()
        }
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

}
