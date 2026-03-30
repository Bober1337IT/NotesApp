package com.bober.notesapp

import com.bober.notesapp.presentation.NotesEndToEndTest
import com.bober.notesapp.presentation.notes.NoteScreenTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    NoteScreenTest::class,
    NotesEndToEndTest::class
)
class AllInstrumentationTests