package com.arpan.notesapp.firebase

import java.io.Serializable

data class Note(
    val title : String? = null,
    val description : String? = null,
    val image : String? = null,
    val dateCreated : String? = null,
    val lastEdited : String? = null
) : Serializable