package com.bober.notesapp.presentation.notes

import com.bober.notesapp.MainDispatcherRule
import com.bober.notesapp.data.repository.FakeTestNoteRepository
import com.bober.notesapp.domain.model.Note
import com.bober.notesapp.domain.use_case.AddNote
import com.bober.notesapp.domain.use_case.DeleteNote
import com.bober.notesapp.domain.use_case.GetNotes
import com.bober.notesapp.domain.util.NoteOrder
import com.bober.notesapp.domain.util.OrderType
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NotesViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: NotesViewModel
    private lateinit var fakeRepository: FakeTestNoteRepository

    @Before
    fun setUp() = runTest {
        fakeRepository = FakeTestNoteRepository()
        val getNotes = GetNotes(fakeRepository)
        val deleteNote = DeleteNote(fakeRepository)
        val addNote = AddNote(fakeRepository)

        viewModel = NotesViewModel(getNotes, addNote, deleteNote)

        val notesToInsert = mutableListOf<Note>()
        ('a'..'z').forEachIndexed { index, ch ->
            notesToInsert.add(
                Note(
                    id = index,
                    title = ch.toString(),
                    content = ch.toString(),
                    timestamp = index.toLong(),
                    color = index
                )
            )
        }
        notesToInsert.shuffle()
        notesToInsert.forEach { fakeRepository.insertNote(it) }

    }

    @Test
    fun `Repository change, state should update automatically`() = runTest {
        val initialSize = viewModel.state.value.notes.size
        assertThat(initialSize).isEqualTo(26)

        val newNote = Note(
            title = "Zzz",
            content = "Content",
            timestamp = 9999L,
            color = 1,
            id = 100
        )
        fakeRepository.insertNote(newNote)

        val updatedNotes = viewModel.state.value.notes
        assertThat(updatedNotes.size).isEqualTo(27)
        assertThat(updatedNotes).contains(newNote)
    }

    @Test
    fun `Order notes by title ascending, state should update correctly`() = runTest {
        assertThat(viewModel.state.value.noteOrder).isInstanceOf(NoteOrder.Date::class.java)
        assertThat(viewModel.state.value.noteOrder.orderType).isEqualTo(OrderType.Descending)

        val newOrder = NoteOrder.Title(OrderType.Ascending)
        viewModel.onEvent(NotesEvent.Order(newOrder))

        assertThat(viewModel.state.value.noteOrder).isInstanceOf(NoteOrder.Title::class.java)
        assertThat(viewModel.state.value.noteOrder.orderType).isEqualTo(OrderType.Ascending)

        val notes = viewModel.state.value.notes
        for (i in 0..notes.size - 2) {
            assertThat(notes[i].title.lowercase()).isAtMost(notes[i + 1].title.lowercase())
        }
    }

    @Test
    fun `Delete note and then Restore note, note should be back in repository`() = runTest {

        val noteToDelete = fakeRepository.getNoteById(1)!!

        viewModel.onEvent(NotesEvent.DeleteNote(noteToDelete))
        var notes = fakeRepository.getNotes().first()
        assertThat(notes).doesNotContain(noteToDelete)

        viewModel.onEvent(NotesEvent.RestoreNote)

        notes = fakeRepository.getNotes().first()
        assertThat(notes).contains(noteToDelete)
    }

    @Test
    fun `Toggle order section, order section should be visible`() = runTest {

        val initialToggle = viewModel.state.value.isOrderSectionVisible
        assertThat(initialToggle).isFalse()

        viewModel.onEvent(NotesEvent.ToggleOrderSection)

        val toggledOnce = viewModel.state.value.isOrderSectionVisible
        assertThat(toggledOnce).isTrue()

        viewModel.onEvent(NotesEvent.ToggleOrderSection)
        val toggledTwice = viewModel.state.value.isOrderSectionVisible
        assertThat(toggledTwice).isFalse()
    }
}