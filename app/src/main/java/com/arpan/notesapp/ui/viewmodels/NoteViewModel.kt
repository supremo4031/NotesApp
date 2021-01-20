package com.arpan.notesapp.ui.viewmodels

import android.util.Log
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NoteViewModel @ViewModelInject constructor(
    private val preference: DataStorePreference,
    private val noteRepository : NoteRepository,
    private val collectionRef : CollectionReference
) : ViewModel() {

    private var uid : String? = null



    private var _isLinear = MutableStateFlow(true)
    val isLinear : StateFlow<Boolean> = _isLinear

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


    private var _noteItems = MutableStateFlow<Resource<List<Note>>>(Resource.initial(null))
    var noteItems : StateFlow<Resource<List<Note>>> = _noteItems

    init {
        _noteItems.value = Resource.loading(null)
        fetchViewType()
        uid = Firebase.auth.currentUser?.uid
        val mStore = collectionRef.whereEqualTo("uid", uid).orderBy("lastEdited", Query.Direction.DESCENDING)
        mStore.addSnapshotListener{value, _ ->
            if(value != null) {
                Log.d("supremo-metadata", value.documents.toString())
                _noteItems.value = Resource.success(value.toObjects(Note::class.java))
            }
        }
    }

    private fun getAllNotes() = viewModelScope.launch {
        val notes = noteRepository.getAllNotes()
        if(notes == null) {
            _noteItems.value = Resource.error("An Unknown Error Occurred", null)
        } else {
            _noteItems.value = Resource.success(notes)
        }
    }

    fun getAllNotesSortedByDate(uid: String) = viewModelScope.launch {
        val notes = noteRepository.getAllNotesSortedByDate(uid)
        if(notes == null) {
            _noteItems.value = Resource.error("An Unknown Error Occurred", null)
        } else {
            _noteItems.value = Resource.success(notes)
        }
    }

    fun getAllNotesSortedByTitle(uid: String) = viewModelScope.launch {
        val notes = noteRepository.getAllNotesSortedByTitle(uid)
        if(notes == null) {
            _noteItems.value = Resource.error("An Unknown Error Occurred", null)
        } else {
            _noteItems.value = Resource.success(notes)
        }
    }

    fun getAllNotesSortedByDescription(uid: String) = viewModelScope.launch {
        val notes = noteRepository.getAllNotesSortedByDescription(uid)
        if(notes == null) {
            _noteItems.value = Resource.error("An Unknown Error Occurred", null)
        } else {
            _noteItems.value = Resource.success(notes)
        }
    }

    fun getAllNotesSortedByLastEdited(uid: String) = viewModelScope.launch {
        val notes = noteRepository.getAllNotesSortedByLastEdited(uid)
        if(notes == null) {
            _noteItems.value = Resource.error("An Unknown Error Occurred", null)
        } else {
            _noteItems.value = Resource.success(notes)
        }
    }
}