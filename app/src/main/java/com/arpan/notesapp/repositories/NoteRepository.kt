package com.arpan.notesapp.repositories

import com.arpan.notesapp.firebase.FirebaseUtils
import com.arpan.notesapp.firebase.Note
import com.google.firebase.firestore.Query
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class NoteRepository @Inject constructor(
    private val firebaseUtils : FirebaseUtils
) {

    fun getAllNotes(uid: String, orderBy: String, type: Query.Direction) = firebaseUtils.getAllNotes(uid, orderBy, type)

    suspend fun insertNote(id : String, note: Note) = firebaseUtils.insertNote(id, note)

    suspend fun updateNote(id : String, note: Note) = firebaseUtils.updateNote(id, note)

    suspend fun deleteNote(id : String) = firebaseUtils.deleteNote(id)
}