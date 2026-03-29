package com.bober.notesapp.domain.use_case

import com.bober.notesapp.data.repository.FakeTestNoteRepository
import com.bober.notesapp.domain.model.InvalidNoteException
import com.bober.notesapp.domain.model.Note
import kotlinx.coroutines.test.runTest
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class AddNoteTest {
    private lateinit var addNote: AddNote
    private lateinit var fakeRepository: FakeTestNoteRepository

    @Before
    fun setUp() {
        fakeRepository = FakeTestNoteRepository()
        addNote = AddNote(fakeRepository)
    }

    @Test
    fun `Insert valid note, successfully adds to repository`() = runTest {
        val note = Note(
            title = "Not blank",
            content = "Not blank",
            timestamp = 1L,
            color = 1,
            id = 99
        )
        addNote(note)

        val result = fakeRepository.getNoteById(99)
        assertThat(result).isEqualTo(note)
    }

    @Test
    fun `Insert note with blank title, throws exception`() = runTest {
        val note = Note(
            title = "",
            content = "Not blank",
            timestamp = 1L,
            color = 1,
            id = 99
        )

        val exception = try {
            addNote(note)
            null
        } catch (e: InvalidNoteException){
            e
        }
        assertThat(exception?.message).isEqualTo("The title of the note can't be empty.")
    }

    @Test
    fun `Insert note with blank content, throws exception`() = runTest {
        val note = Note(
            title = "Not blank",
            content = "",
            timestamp = 1L,
            color = 1,
            id = 99
        )

        val exception = try {
            addNote(note)
            null
        } catch (e: InvalidNoteException){
            e
        }
        assertThat(exception?.message).isEqualTo("The content of the note can't be empty.")
    }
}