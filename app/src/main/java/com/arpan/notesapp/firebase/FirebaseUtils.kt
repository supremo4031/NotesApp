package com.arpan.notesapp.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class FirebaseUtils {

    private var notesCollection = Firebase.firestore.collection("notes")

    suspend fun getAllNotes() : List<Note>? {
        return try {
            notesCollection.get().await().toObjects(Note::class.java)
        } catch (e : Exception) {
            emptyList()
        }
    }

    suspend fun getAllNotesSortedByDate() : List<Note>? {
        return try {
            notesCollection.orderBy("dateCreated").get().await().toObjects(Note::class.java)
        } catch (e : Exception) {
            emptyList()
        }
    }

    suspend fun getAllNotesSortedByTitle() : List<Note>? {
        return try {
            notesCollection.orderBy("title").get().await().toObjects(Note::class.java)
        } catch (e : Exception) {
            emptyList()
        }
    }

    suspend fun getAllNotesSortedByDescription() : List<Note>? {
        return try {
            notesCollection.orderBy("description").get().await().toObjects(Note::class.java)
        } catch (e : Exception) {
            emptyList()
        }
    }

    suspend fun getAllNotesSortedByLastEdited() : List<Note>? {
        return try {
            notesCollection.orderBy("lastEdited").get().await().toObjects(Note::class.java)
        } catch (e : Exception) {
            emptyList()
        }
    }

    suspend fun insertNote(uid : String, note: Note) : Boolean {
        return  try {
            notesCollection.add(note).await()
            true
        } catch (e : java.lang.Exception) {
            false
        }
    }

    suspend fun updateNote(uid : String, note: Note) : Boolean {
        return try {
            notesCollection.document(uid).update(
                "title", note.title,
                "description", note.description,
                "image", note.image,
                "dateCreated", note.dateCreated,
                "lastEdited", note.lastEdited
            ).await()
            true
        } catch (e : Exception) {
            false
        }
    }

}
