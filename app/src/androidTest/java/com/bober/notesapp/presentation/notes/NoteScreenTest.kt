package com.bober.notesapp.presentation.notes

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.bober.notesapp.core.util.TestTags
import com.bober.notesapp.di.AppModule
import com.bober.notesapp.domain.model.Note
import com.bober.notesapp.domain.repository.NoteRepository
import com.bober.notesapp.presentation.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
@UninstallModules(AppModule::class)
class NoteScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var repository: NoteRepository

    @Before
    fun setUp(){
        hiltRule.inject()
    }

    @Test
    fun clickToggleOrderSection_isVisible() {
        composeRule.onNodeWithTag(TestTags.ORDER_SECTION).assertDoesNotExist()
        composeRule.onNodeWithContentDescription("Sort").performClick()
        composeRule.onNodeWithTag(TestTags.ORDER_SECTION).assertIsDisplayed()
    }

    @Test
    fun clickToggleOrderSectionAndChangeToTitle_titleIsClicked() {
        composeRule.onNodeWithTag(TestTags.ORDER_SECTION).assertDoesNotExist()
        composeRule.onNodeWithContentDescription("Sort").performClick()

        val titleRadioButton = composeRule
            .onNodeWithTag(TestTags.TITLE_RADIO_BUTTON, useUnmergedTree = true)
            .onChildren()
            .filterToOne(hasClickAction())

        titleRadioButton.assertIsNotSelected()
        composeRule.onNodeWithText("Title").assertIsDisplayed()
        titleRadioButton.performClick()
        titleRadioButton.assertIsSelected()
    }

    @Test
    fun clickAddNote_navigatesToAddEditNoteScreen() {
        composeRule.onNodeWithText("Your note").assertIsDisplayed()
        composeRule.onNodeWithContentDescription("Add Note").performClick()
        composeRule.onNodeWithText("Enter title...").assertIsDisplayed()
    }

    @Test
    fun deleteNote_isRemovedFromDatabase() = runBlocking {
        val note = Note(title = "test", content = "content", timestamp = 1L, color = 1, id = 1)
        repository.insertNote(note)

        composeRule.onNodeWithText("test").assertIsDisplayed()

        composeRule.onNodeWithContentDescription("Delete note test").performClick()

        composeRule.onNodeWithText("test").assertDoesNotExist()

        val dbNote = repository.getNoteById(1)
        assert(dbNote == null)
    }
}