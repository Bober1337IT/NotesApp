package com.bober.notesapp.domain.use_case

import com.bober.notesapp.data.repository.FakeTestNoteRepository
import com.bober.notesapp.domain.model.Note
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetNoteTest {

    private lateinit var fakeRepository: FakeTestNoteRepository
    private lateinit var getNote: GetNote

    @Before
    fun setUp() {
        fakeRepository = FakeTestNoteRepository()
        getNote = GetNote(fakeRepository)

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
    fun `Get note by id, returns correct note`() = runTest {

        val note = getNote(1)

        assertThat(note).isNotNull()
        assertThat(note?.title).isEqualTo("title")
        assertThat(note?.id).isEqualTo(1)
    }

    @Test
    fun `Get note by non-existing id, returns null`() = runTest {
        val note = getNote(99)

        assertThat(note).isNull()
    }
}