package com.arpan.notesapp.firebase

import java.io.Serializable

data class Note (
        var id : String? = null,
        var uid : String? = null,
        var title : String? = null,
        var description : String? = null,
        var image : String? = null,
        var dateCreated : String? = null,
        var lastEdited : String? = null
) : Serializable