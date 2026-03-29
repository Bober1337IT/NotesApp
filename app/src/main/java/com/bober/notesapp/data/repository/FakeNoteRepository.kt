package com.bober.notesapp.data.repository

import com.bober.notesapp.domain.model.Note
import com.bober.notesapp.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class FakeNoteRepository @Inject constructor(): NoteRepository {

    private val fakeContent =
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec vestibulum vel arcu vel ullamcorper. Integer venenatis diam ac facilisis accumsan. Phasellus lorem felis, sollicitudin non justo ut, posuere dignissim nunc. Nunc dignissim volutpat mauris quis rhoncus. Curabitur tempor ipsum non lacus porttitor mollis. Etiam dictum eros eros, at auctor augue consequat a. Fusce a arcu ac est maximus hendrerit at lacinia tellus.In feugiat dui non laoreet vestibulum. Nunc vulputate id mauris a iaculis. Curabitur vitae tempus justo. Morbi a est et sapien maximus porta eu ac ante. Cras gravida feugiat elementum. Vestibulum dictum volutpat est sit amet commodo. Suspendisse sed lectus magna. Pellentesque eget augue sed ipsum aliquet tincidunt. Interdum et malesuada fames ac ante ipsum primis in faucibus. Nulla sed auctor arcu. Proin ligula nisl, auctor ac ornare eu, mattis quis lorem. Phasellus et gravida metus, vitae tincidunt nibh. Nam tempus, libero porttitor mattis suscipit, neque neque viverra nunc, non ultrices nibh odio non turpis. Vestibulum dui sem, congue vel tellus ac, convallis iaculis mauris. Integer vulputate, augue bibendum congue ullamcorper, lacus enim luctus magna, sed iaculis magna enim eget augue."

    // This replaces a real database during development or testing
    private val notesFlow = MutableStateFlow(
        listOf(
            Note(id = 1, title = "First Note", content = fakeContent, timestamp = 1L, color = 0xFFFFAB91.toInt()),
            Note(id = 2, title = "Second Note", content = fakeContent, timestamp = 2L, color = 0xFFE7ED9B.toInt()),
            Note(id = 3, title = "Third Note", content = fakeContent, timestamp = 3L, color = 0xFFD7AEFB.toInt())
        )
    )

    override fun getNotes(): Flow<List<Note>> {
        return notesFlow
    }

    override suspend fun getNoteById(id: Int): Note? {
        return notesFlow.value.find { it.id == id }
    }

    override suspend fun insertNote(note: Note) {
        notesFlow.update { currentNotes ->
            currentNotes + note
        }
    }

    override suspend fun deleteNote(note: Note) {
        notesFlow.update { currentNotes ->
            currentNotes.filter { it.id != note.id }
        }
    }
}