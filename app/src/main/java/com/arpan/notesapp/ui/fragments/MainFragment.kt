package com.arpan.notesapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.arpan.notesapp.R
import com.arpan.notesapp.adpaters.NoteAdapter
import com.arpan.notesapp.firebase.Note
import com.arpan.notesapp.others.Status
import com.arpan.notesapp.ui.viewmodels.NoteViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.flow.collect

class MainFragment : Fragment(R.layout.fragment_main) {


    private var noteAdapter : NoteAdapter = NoteAdapter()

    private val noteViewModel : NoteViewModel by activityViewModels()

    private val mUser = Firebase.auth.currentUser
    private val uid = mUser?.uid

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
            val bundle = Bundle().apply {
                putSerializable("notes", Note())
                putString("uid", uid ?: "")
            }
            findNavController().navigate (
                    R.id.action_mainFragment_to_editFragment,
                    bundle
            )
        }


        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                when(pos) {
                    0 -> {
                        uid?.let { noteViewModel.getAllNotesSortedByLastEdited(it) }
                    }
                    1 -> {
                        uid?.let { noteViewModel.getAllNotesSortedByDate(it) }
                    }
                    2 -> {
                        uid?.let { noteViewModel.getAllNotesSortedByDescription(it) }
                    }
                    3 -> {
                        uid?.let { noteViewModel.getAllNotesSortedByTitle(it) }
                    }
                    else -> {
                        uid?.let { noteViewModel.getAllNotesSortedByLastEdited(it) }
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        lifecycleScope.launchWhenCreated {
            noteViewModel.noteItems.collect { result ->
                when(result.status) {
                    Status.SUCCESS -> {
                        // if(main_progressbar.visibility == View.VISIBLE)
//                            main_progressbar.visibility = View.GONE
                        result.data?.let {
                            Log.d("supremo", it.toString())
                            noteAdapter.updateList(it)
                        }
                    }
                    Status.LOADING -> {
                        Toast.makeText(requireContext(), "Loading data, Please wait...", Toast.LENGTH_SHORT).show()
//                        main_progressbar.visibility = View.VISIBLE
                    }
                    Status.ERROR ->  {
//                        main_progressbar.visibility = View.GONE
                        Toast.makeText(requireContext(), "Not getting data", Toast.LENGTH_SHORT).show()
                    }
                    else -> {

                    }
                }
            }
        }

        lifecycleScope.launchWhenCreated {
            noteViewModel.isLinear.collect {

                main_recyclerView.layoutManager = if(it) {
                    LinearLayoutManager(requireContext())
                }
                else {
                    StaggeredGridLayoutManager(
                            2,
                            LinearLayoutManager.VERTICAL
                    )
                }
            }
        }
    }

}
