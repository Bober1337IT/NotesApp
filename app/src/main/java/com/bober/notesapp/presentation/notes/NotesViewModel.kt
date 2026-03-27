package com.bober.notesapp.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bober.notesapp.domain.model.Note
import com.bober.notesapp.domain.use_case.AddNote
import com.bober.notesapp.domain.use_case.DeleteNote
import com.bober.notesapp.domain.use_case.GetNotes
import com.bober.notesapp.domain.util.NoteOrder
import com.bober.notesapp.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val getNotes: GetNotes,
    private val addNote: AddNote,
    private val deleteNote: DeleteNote
) : ViewModel() {

    private val _state = mutableStateOf(NotesState())
    val state: State<NotesState> = _state

    private var recentlyDeleteNote: Note? = null

    private var loadNotesJob: Job? = null

    init {
        loadNotes(NoteOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.Order -> {
                // Check if the new order is the same as the current one to avoid unnecessary database queries and UI recompositions
                if (state.value.noteOrder::class == event.noteOrder::class &&
                    state.value.noteOrder.orderType == event.noteOrder.orderType
                ) {
                    return
                }
                loadNotes(event.noteOrder)
            }

            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    deleteNote(event.note)
                    recentlyDeleteNote = event.note
                }
            }

            is NotesEvent.RestoreNote -> {
                viewModelScope.launch {
                    addNote(recentlyDeleteNote ?: return@launch)
                    recentlyDeleteNote = null
                }
            }

            is NotesEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }
    private fun loadNotes(noteOrder: NoteOrder) {
        // Cancel the previous coroutine job before starting a new one
        // This prevents multiple active database subscriptions from running
        // simultaneously, which could lead to memory leaks and conflicting UI updates
        loadNotesJob?.cancel()

        loadNotesJob = getNotes.invoke(noteOrder)
            .onEach { notes ->
                _state.value = state.value.copy(
                    notes = notes,
                    noteOrder = noteOrder
                )
            }
            .launchIn(viewModelScope)
    }
}