package com.bober.notesapp.domain.use_case

import com.bober.notesapp.data.repository.FakeTestNoteRepository
import com.bober.notesapp.domain.model.Note
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class DeleteNoteTest {

    private lateinit var fakeRepository: FakeTestNoteRepository
    private lateinit var deleteNote: DeleteNote

    @Before
    fun setUp() {
        fakeRepository = FakeTestNoteRepository()
        deleteNote = DeleteNote(fakeRepository)

        runTest {
            fakeRepository.insertNote(
                Note(
                    id = 1,
                    title = "title",
                    content = "content",
                    timestamp = 1L,
                    color = 1
                )
            )
        }
    }

    @Test
    fun `Delete note by id, delete correct note`() = runTest{
        val noteToDelete = fakeRepository.getNoteById(1)
        noteToDelete?.let { deleteNote(it) }
        val result = fakeRepository.getNoteById(1)
        assertThat(result).isNull()
    }
}