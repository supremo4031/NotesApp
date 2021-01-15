package com.arpan.notesapp.repositories

import com.arpan.notesapp.firebase.FirebaseUtils
import com.arpan.notesapp.firebase.Note
import kotlinx.coroutines.tasks.await

class NoteRepository {

    private val firebaseUtils = FirebaseUtils()

    suspend fun getAllNotes() = firebaseUtils.getAllNotes()

    suspend fun getAllNotesSortedByDate() = firebaseUtils.getAllNotesSortedByDate()

    suspend fun getAllNotesSortedByTitle() = firebaseUtils.getAllNotesSortedByTitle()

    suspend fun getAllNotesSortedByDescription() = firebaseUtils.getAllNotesSortedByDescription()

    suspend fun getAllNotesSortedByLastEdited() = firebaseUtils.getAllNotesSortedByLastEdited()

    suspend fun insertNote(uid : String, note: Note) = firebaseUtils.insertNote(uid, note)

    suspend fun updateNote(uid : String, note: Note) = firebaseUtils.updateNote(uid, note)
}