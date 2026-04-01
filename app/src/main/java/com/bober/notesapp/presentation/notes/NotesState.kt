package com.bober.notesapp.presentation.notes

import androidx.compose.runtime.Immutable
import com.bober.notesapp.domain.model.Note
import com.bober.notesapp.domain.util.NoteOrder
import com.bober.notesapp.domain.util.OrderType

@Immutable
data class NotesState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)
