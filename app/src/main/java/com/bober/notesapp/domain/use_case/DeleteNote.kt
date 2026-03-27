package com.bober.notesapp.domain.use_case

import com.bober.notesapp.domain.model.Note
import com.bober.notesapp.domain.repository.NoteRepository

class DeleteNote(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(note: Note){
        repository.deleteNote(note)
    }
}