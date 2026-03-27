package com.bober.notesapp.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bober.notesapp.domain.model.Note
import com.bober.notesapp.domain.use_case.DeleteNote
import com.bober.notesapp.domain.use_case.GetNotes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val getNotes: GetNotes,
    private val deleteNote: DeleteNote
): ViewModel(){

    private val _state = mutableStateOf(NotesState())
    val state: State<NotesState> = _state

    private var recentlyDeleteNote: Note? = null

    fun onEvent(event: NotesEvent){
        when(event){
            is NotesEvent.Order -> {
                // TODO
            }
            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    deleteNote(event.note)
                    recentlyDeleteNote = event.note
                }
            }
            is NotesEvent.RestoreNote -> {
                viewModelScope.launch {
                    // TODO
                }
            }
            is NotesEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }
}