package com.bober.notesapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.bober.notesapp.presentation.add_edit_note.AddEditNoteScreen
import com.bober.notesapp.presentation.notes.NoteScreen
import com.bober.notesapp.presentation.ui.theme.NotesAppTheme
import com.bober.notesapp.presentation.util.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NotesAppTheme {
                Surface {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.NotesScreen
                    ) {
                        composable<Screen.NotesScreen>{
                            NoteScreen(navController = navController)
                        }
                        composable<Screen.AddEditNoteScreen> { backStackEntry ->
                            val args = backStackEntry.toRoute<Screen.AddEditNoteScreen>()
                            AddEditNoteScreen(
                                navController = navController,
                                noteColor = args.noteColor ?: -1
                            )
                        }
                    }
                }
            }
        }
    }
}
