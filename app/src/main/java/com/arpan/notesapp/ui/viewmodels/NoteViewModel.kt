package com.arpan.notesapp.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arpan.notesapp.datastore.DataStorePreference
import com.arpan.notesapp.firebase.Note
import com.arpan.notesapp.others.Resource
import com.arpan.notesapp.repositories.NoteRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class NoteViewModel @ViewModelInject constructor(
    private val preference: DataStorePreference,
    private val noteRepository : NoteRepository,
    collectionRef : CollectionReference
) : ViewModel() {

    private var uid : String

    private var _query = MutableStateFlow("")
    val query : StateFlow<String> = _query
    private var _isLinear = MutableStateFlow(true)
    val isLinear : StateFlow<Boolean> = _isLinear
    private var _noteItems = MutableStateFlow<Resource<List<Note>>>(Resource.initial(null))
    var noteItems : StateFlow<Resource<List<Note>>> = _noteItems

    init {
        _noteItems.value = Resource.loading(null)
        uid = Firebase.auth.currentUser?.uid ?: ""
        fetchViewType()
    }

    fun selectQuery(search: String) {
        _query.value = search
    }

    fun changeLayout() {
        _isLinear.value = !isLinear.value
        saveViewType(_isLinear.value)
    }

    private fun saveViewType(save: Boolean) {
        viewModelScope.launch {
            preference.saveViewType(save)
        }
    }

    private fun fetchViewType() = viewModelScope.launch {
        _isLinear.value = preference.fetchViewType()
    }

    fun getAllNotes(uid: String, orderBy: String, type: Query.Direction) = viewModelScope.launch {
        noteRepository.getAllNotes(uid, orderBy, type)
                .catch {
                    _noteItems.value = Resource.error("An unknown error occured", null)
                }.collect { notes ->
            _noteItems.value = Resource.success(notes)
        }
    }

    fun insertNote(id : String, note: Note) = viewModelScope.launch {
        noteRepository.insertNote(id, note)
    }

    fun updateNote(id : String, note: Note) = viewModelScope.launch {
        noteRepository.updateNote(id, note)
    }

    fun deleteNote(id: String) = viewModelScope.launch {
        noteRepository.deleteNote(id)
    }
}