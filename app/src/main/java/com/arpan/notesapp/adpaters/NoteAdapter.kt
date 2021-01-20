package com.arpan.notesapp.adpaters

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arpan.notesapp.R
import com.arpan.notesapp.firebase.Note
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.note_layout.view.*

class NoteAdapter: RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    class NoteViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.note_layout,
                parent,
                false
            )
        )
    }

    private val differCallback = object : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.dateCreated == newItem.dateCreated
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private var differ = AsyncListDiffer(
        this,
        differCallback
    )

    fun updateList(notes : List<Note>) {
        differ.submitList(notes)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = differ.currentList[position]
        holder.itemView.apply {
            Log.d("supremo", "OnBindViewHolder Called")
            if(note.image != null) {
                note_image.visibility = View.VISIBLE
                Glide.with(this)
                    .load(note.image)
                    .into(note_image)
            }
            note_title.text = note.title
            note_text.text = note.description

            setOnClickListener {
                val bundle = Bundle().apply {
                    putSerializable("notes", note)
                    putString("uid", Firebase.auth.currentUser?.uid ?: "")
                }
                findNavController().navigate(
                        R.id.action_mainFragment_to_editFragment,
                        bundle
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }



}