package com.bober.notesapp.presentation.util

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    object NotesScreen

    @Serializable
    data class AddEditNoteScreen(
        val noteId: Int? = -1,
        val noteColor: Int? = -1
    )
}
