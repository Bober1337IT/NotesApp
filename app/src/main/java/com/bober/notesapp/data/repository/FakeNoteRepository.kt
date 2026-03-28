package com.bober.notesapp.data.repository

import com.bober.notesapp.domain.model.Note
import com.bober.notesapp.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class FakeNoteRepository @Inject constructor(): NoteRepository {

    private val notes = mutableListOf<Note>(
        Note(id = 1, title = "First Note", content = "Content 1", timestamp = 1L, color = 0xFFFFAB91.toInt()),
        Note(id = 2, title = "Second Note", content = "Content 2", timestamp = 2L, color = 0xFFE7ED9B.toInt()),
        Note(id = 3, title = "Third Note", content = "Content 3", timestamp = 3L, color = 0xFFD7AEFB.toInt())
    )
    private val _notesFlow = MutableStateFlow<List<Note>>(notes)

    override fun getNotes(): Flow<List<Note>> {
        return _notesFlow.asStateFlow()
    }

    override suspend fun getNoteById(id: Int): Note? {
        return notes.find { it.id == id }
    }

    override suspend fun insertNote(note: Note) {
        val existingNote = notes.find { it.id == note.id }
        if (existingNote != null) {
            val index = notes.indexOf(existingNote)
            notes[index] = note
        } else {
            notes.add(note.copy(id = (notes.maxOfOrNull { it.id ?: 0 } ?: 0) + 1))
        }
        _notesFlow.value = notes.toList()
    }

    override suspend fun deleteNote(note: Note) {
        notes.removeIf { it.id == note.id }
        _notesFlow.value = notes.toList()
    }
}