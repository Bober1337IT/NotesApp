package com.bober.notesapp.data.repository

import com.bober.notesapp.domain.model.Note
import com.bober.notesapp.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class FakeTestNoteRepository @Inject constructor(): NoteRepository {

    private val notes = MutableStateFlow<List<Note>>(emptyList())

    override fun getNotes(): Flow<List<Note>> {
        return (notes)
    }

    override suspend fun getNoteById(id: Int): Note? {
        return notes.value.find { it.id == id }
    }

    override suspend fun insertNote(note: Note) {
        notes.update {
            it + note
        }
    }

    override suspend fun deleteNote(note: Note) {
        notes.update { currentNotes ->
            currentNotes.filter { it.id != note.id }
        }
    }
}
