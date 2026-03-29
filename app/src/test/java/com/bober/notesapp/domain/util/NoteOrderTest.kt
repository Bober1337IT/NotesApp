package com.bober.notesapp.domain.util

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class NoteOrderTest {

    @Test
    fun `NoteOrder Title copy with Descending, returns Title Descending`() {
        val noteOrder = NoteOrder.Title(OrderType.Ascending)
        val newOrder = noteOrder.copy(OrderType.Descending)

        assertThat(newOrder).isInstanceOf(NoteOrder.Title::class.java)
        assertThat(newOrder.orderType).isEqualTo(OrderType.Descending)
    }

    @Test
    fun `NoteOrder Date copy with Ascending, returns Date Ascending`() {
        val noteOrder = NoteOrder.Date(OrderType.Descending)
        val newOrder = noteOrder.copy(OrderType.Ascending)

        assertThat(newOrder).isInstanceOf(NoteOrder.Date::class.java)
        assertThat(newOrder.orderType).isEqualTo(OrderType.Ascending)
    }

    @Test
    fun `NoteOrder Color copy with Descending, returns Color Descending`() {
        val noteOrder = NoteOrder.Color(OrderType.Ascending)
        val newOrder = noteOrder.copy(OrderType.Descending)

        assertThat(newOrder).isInstanceOf(NoteOrder.Color::class.java)
        assertThat(newOrder.orderType).isEqualTo(OrderType.Descending)
    }

    @Test
    fun `NoteOrder Title with Ascending, has correct initial orderType`() {
        val noteOrder = NoteOrder.Title(OrderType.Ascending)

        assertThat(noteOrder.orderType).isEqualTo(OrderType.Ascending)
    }

}