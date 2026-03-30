package com.bober.notesapp.presentation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTextReplacement
import com.bober.notesapp.core.util.TestTags
import com.bober.notesapp.di.AppModule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class NotesEndToEndTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp(){
        hiltRule.inject()
    }

    @Test
    fun saveNewNote_editAfterwards() {

        composeRule.onNodeWithContentDescription("Add Note").performClick()

        composeRule.onNodeWithTag(TestTags.TITLE_TEXT_FIELD, useUnmergedTree = true)
            .onChildren()
            .filterToOne(hasSetTextAction())
            .performTextInput("Test Title")

        composeRule.onNodeWithTag(TestTags.CONTENT_TEXT_FIELD, useUnmergedTree = true)
            .onChildren()
            .filterToOne(hasSetTextAction())
            .performTextInput("Test Content")

        composeRule.onNodeWithContentDescription("Save note").performClick()

        composeRule.onNodeWithText("Test Title")
            .assertIsDisplayed()
            .performClick()

        composeRule.onNodeWithTag(TestTags.TITLE_TEXT_FIELD, useUnmergedTree = true)
            .onChildren()
            .filterToOne(hasSetTextAction())
            .assertTextEquals("Test Title")
            .performTextReplacement("Test Title 2")

        composeRule.onNodeWithContentDescription("Save note").performClick()

        composeRule.onNodeWithText("Test Title 2").assertIsDisplayed()
    }

}