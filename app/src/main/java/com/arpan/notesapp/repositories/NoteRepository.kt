package com.arpan.notesapp.repositories

import com.arpan.notesapp.firebase.FirebaseUtils
import com.arpan.notesapp.firebase.Note
import javax.inject.Inject

class NoteRepository @Inject constructor(
    private val firebaseUtils : FirebaseUtils
) {

    suspend fun getAllNotes() = firebaseUtils.getAllNotes()

    suspend fun getAllNotesSortedByDate(uid: String) = firebaseUtils.getAllNotesSortedByDate(uid)

    suspend fun getAllNotesSortedByTitle(uid: String) = firebaseUtils.getAllNotesSortedByTitle(uid)

    suspend fun getAllNotesSortedByDescription(uid: String) = firebaseUtils.getAllNotesSortedByDescription(uid)

    suspend fun getAllNotesSortedByLastEdited(uid: String) = firebaseUtils.getAllNotesSortedByLastEdited(uid)

    suspend fun insertNote(id : String, note: Note) = firebaseUtils.insertNote(id, note)

    suspend fun updateNote(id : String, note: Note) = firebaseUtils.updateNote(id, note)

    suspend fun deleteNote(id : String) = firebaseUtils.deleteNote(id)
}