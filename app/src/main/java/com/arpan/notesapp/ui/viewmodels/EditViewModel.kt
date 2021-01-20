package com.arpan.notesapp.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arpan.notesapp.firebase.Note
import com.arpan.notesapp.repositories.NoteRepository
import kotlinx.coroutines.launch

class EditViewModel @ViewModelInject constructor(
    private val noteRepository : NoteRepository
) : ViewModel() {


    fun insertNote(id : String, note: Note) = viewModelScope.launch {
        noteRepository.insertNote(id, note)
    }

    fun updateNote(id : String, note: Note) = viewModelScope.launch {
        noteRepository.updateNote(id, note)
    }
}