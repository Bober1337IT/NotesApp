package com.bober.notesapp.data.repository

import android.app.Application
import com.bober.notesapp.R
import com.bober.notesapp.domain.model.Note
import com.bober.notesapp.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class FakeNoteRepository @Inject constructor(
    private val app: Application
): NoteRepository {

    // This replaces a real database during development or testing
    private val _notesFlow = MutableStateFlow<List<Note>>(
        listOf(
            Note(id = 1, title = "First Note", content = app.getString(R.string.fake_content), timestamp = 1L, color = 0xFFFFAB91.toInt()),
            Note(id = 2, title = "Second Note", content = app.getString(R.string.fake_content), timestamp = 2L, color = 0xFFE7ED9B.toInt()),
            Note(id = 3, title = "Third Note", content = app.getString(R.string.fake_content), timestamp = 3L, color = 0xFFD7AEFB.toInt())
        )
    )
    override fun getNotes(): Flow<List<Note>> {
        return _notesFlow.asStateFlow()
    }

    override suspend fun getNoteById(id: Int): Note? {
        return _notesFlow.value.find { it.id == id }
    }

    override suspend fun insertNote(note: Note) {
        _notesFlow.update { currentNotes ->
            val existingNote = currentNotes.find { it.id == note.id }
            if (existingNote != null) {
                currentNotes.map { if (it.id == note.id) note else it }
            } else {
                val newId = (currentNotes.maxOfOrNull { it.id ?: 0 } ?: 0) + 1
                currentNotes + note.copy(id = newId)
            }
        }
    }

    override suspend fun deleteNote(note: Note) {
        println("Repository: Deleting note ${note.id}")
        _notesFlow.update { currentNotes ->
            currentNotes.filter { it.id != note.id }
        }
        println("Repository: Remaining notes: ${_notesFlow.value.size}")
    }
}