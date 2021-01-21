package com.arpan.notesapp.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.arpan.notesapp.R
import com.arpan.notesapp.ui.viewmodels.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_edit.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class EditFragment : Fragment(R.layout.fragment_edit) {

    private val args : EditFragmentArgs by navArgs()

    private val viewModel : NoteViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val note = args.notes
        val uid = args.uid

        note.let {
            edit_notes_title.setText(note.title)
            edit_notes_text.setText(note.description)
        }

        edit_notes_text.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                note.title = edit_notes_title.text.toString()
                note.description = edit_notes_text.text.toString()
                val currentTime = System.currentTimeMillis().toString()
                note.lastEdited = currentTime
                if(note.dateCreated == null) {
                    note.id = "${currentTime}${uid}"
                    note.dateCreated = currentTime
                    note.uid = uid
                    viewModel.insertNote(note.id!!, note)
                } else {
                    viewModel.updateNote(note.id!!, note)
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        edit_notes_title.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                note.title = edit_notes_title.text.toString()
                note.description = edit_notes_text.text.toString()
                val currentTime = System.currentTimeMillis().toString()
                note.lastEdited = currentTime
                if(note.dateCreated == null) {
                    note.id = "${currentTime}${uid}"
                    note.dateCreated = currentTime
                    note.uid = uid
                    viewModel.insertNote(note.id!!, note)
                } else {
                    viewModel.updateNote(note.id!!, note)
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

    }
}