package com.bober.notesapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bober.notesapp.domain.model.Note

@Entity
data class NoteEntity(
    @PrimaryKey val id: Int? = null,
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int
)

// Map NoteEntity to Note
fun NoteEntity.toNote(): Note {
    return Note(
        id = id,
        title = title,
        content = content,
        timestamp = timestamp,
        color = color
    )
}

// Map Note to NoteEntity
fun Note.toNoteEntity(): NoteEntity {
    return NoteEntity(
        id = id,
        title = title,
        content = content,
        timestamp = timestamp,
        color = color
    )
}
