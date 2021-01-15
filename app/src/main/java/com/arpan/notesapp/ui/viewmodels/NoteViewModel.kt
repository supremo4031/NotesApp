package com.arpan.notesapp.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arpan.notesapp.firebase.Note
import com.arpan.notesapp.others.Resource
import com.arpan.notesapp.repositories.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NoteViewModel : ViewModel() {

    private val noteRepository = NoteRepository()

    private var _noteItems = MutableStateFlow<Resource<List<Note>>>(Resource.initial(null))
    var noteItems : StateFlow<Resource<List<Note>>> = _noteItems

    init {
        _noteItems.value = Resource.loading(null)
        getAllNotes()
    }

    private fun getAllNotes() = viewModelScope.launch {
        val notes = noteRepository.getAllNotes()
        if(notes == null) {
            _noteItems.value = Resource.error("An Unknown Error Occurred", null)
        } else {
            _noteItems.value = Resource.success(notes)
        }
    }

    fun getAllNotesSortedByDate() = viewModelScope.launch {
        noteRepository.getAllNotesSortedByDate()
    }

    fun getAllNotesSortedByTitle() = viewModelScope.launch {
        noteRepository.getAllNotesSortedByTitle()
    }

    fun getAllNotesSortedByDescription() = viewModelScope.launch {
        noteRepository.getAllNotesSortedByDescription()
    }

    fun getAllNotesSortedByLastEdited() = viewModelScope.launch {
        noteRepository.getAllNotesSortedByLastEdited()
    }

    fun insertNote(uid : String, note: Note) = viewModelScope.launch {
        noteRepository.insertNote(uid, note)
    }

    fun updateNote(uid : String, note: Note) = viewModelScope.launch {
        noteRepository.updateNote(uid, note)
    }
}