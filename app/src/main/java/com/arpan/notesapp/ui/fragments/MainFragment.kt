package com.arpan.notesapp.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.arpan.notesapp.R
import com.arpan.notesapp.adpaters.NoteAdapter
import com.arpan.notesapp.others.Status
import com.arpan.notesapp.ui.viewmodels.NoteViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.flow.collect

class MainFragment : Fragment(R.layout.fragment_main) {


    private var noteAdapter : NoteAdapter = NoteAdapter()

    private val noteViewModel : NoteViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        main_recyclerView.apply {
            adapter = noteAdapter
            layoutManager = StaggeredGridLayoutManager(
                2,
                LinearLayoutManager.VERTICAL
            )
            setHasFixedSize(true)
        }

        main_fab.setOnClickListener {

        }

        lifecycleScope.launchWhenCreated {
            noteViewModel.noteItems.collect { result ->
                when(result.status) {
                    Status.SUCCESS -> {
                        result.data?.let {
                            noteAdapter.updateList(it)

                        }
                    }
                    else ->  {
                        Unit
                        Toast.makeText(requireContext(), "Not getting data", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}