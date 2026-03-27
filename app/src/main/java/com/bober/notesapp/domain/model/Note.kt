package com.bober.notesapp.domain.model

data class Note(
    val id: Int? = null,
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int
) {
    companion object {
        val noteColors = listOf(0xffffab91, 0xffe7ed9b, 0xffd7aefb, 0xff81deea, 0xfff48fb1)
    }
}
