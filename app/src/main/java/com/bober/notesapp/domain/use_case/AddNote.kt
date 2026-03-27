package com.bober.notesapp.domain.use_case

import com.bober.notesapp.domain.model.InvalidNoteException
import com.bober.notesapp.domain.model.Note
import com.bober.notesapp.domain.repository.NoteRepository
import javax.inject.Inject

class AddNote @Inject constructor(
    private val repository: NoteRepository
) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note){

        if(note.title.isBlank()){
            throw InvalidNoteException("The title of the note can't be empty.")
        }
        if(note.content.isBlank()){
            throw InvalidNoteException("The content of the note can't be empty.")
        }
        repository.insertNote(note)
    }
}