package com.bober.notesapp.presentation.add_edit_note

import androidx.lifecycle.SavedStateHandle
import com.bober.notesapp.MainDispatcherRule
import com.bober.notesapp.data.repository.FakeTestNoteRepository
import com.bober.notesapp.domain.model.Note
import com.bober.notesapp.domain.use_case.AddNote
import com.bober.notesapp.domain.use_case.GetNote
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AddEditNoteViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: AddEditNoteViewModel
    private lateinit var fakeRepository: FakeTestNoteRepository
    private lateinit var getNote: GetNote
    private lateinit var addNote: AddNote

    @Before
    fun setUp() {
        fakeRepository = FakeTestNoteRepository()
        getNote = GetNote(fakeRepository)
        addNote = AddNote(fakeRepository)
    }

    @Test
    fun `Enter title, state should update correctly`() {
        viewModel = AddEditNoteViewModel(SavedStateHandle(), getNote, addNote)
        val title = "New Title"
        viewModel.onEvent(AddEditNoteEvent.EnteredTitle(title))

        assertThat(viewModel.noteTitle.value.text).isEqualTo(title)
    }

    @Test
    fun `Save note, should be added to repository`() = runTest {
        viewModel = AddEditNoteViewModel(SavedStateHandle(), getNote, addNote)

        viewModel.onEvent(AddEditNoteEvent.EnteredTitle("Test Title"))
        viewModel.onEvent(AddEditNoteEvent.EnteredContent("Test Content"))
        viewModel.onEvent(AddEditNoteEvent.SaveNote)

        mainDispatcherRule.testDispatcher.scheduler.advanceUntilIdle()

        val notes = fakeRepository.getNotes().first()
        assertThat(notes.any { it.title == "Test Title" && it.content == "Test Content" }).isTrue()
    }

    @Test
    fun `Save empty note, should emit snackbar event`() = runTest {
        viewModel = AddEditNoteViewModel(SavedStateHandle(), getNote, addNote)

        val events = mutableListOf<AddEditNoteViewModel.UiEvent>()
        val job = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.eventFlow.collect { events.add(it) }
        }

        // Title and content are empty by default
        viewModel.onEvent(AddEditNoteEvent.SaveNote)

        assertThat(events).isNotEmpty()
        assertThat(events.first()).isInstanceOf(AddEditNoteViewModel.UiEvent.ShowSnackbar::class.java)

        job.cancel()
    }
}
