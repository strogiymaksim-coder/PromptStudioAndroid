package com.msksim.promptstudio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    var currentScreen by remember { mutableStateOf("welcome") }

                    // Общие списки данных для сохранения состояния в приложении
                    val projectsList = remember { mutableStateListOf<PromptProject>() }
                    val customLibraries = remember { mutableStateListOf<LibraryItem>() }
                    val customPalettes = remember { mutableStateListOf<ColorPalette>() }

                    // Временные переменные для текущего создаваемого промта
                    var currentPromptContent by remember { mutableStateOf("") }

                    when (currentScreen) {
                        "welcome" -> WelcomeScreen(
                            onStartClick = { currentScreen = "home" }
                        )
                        "home" -> HomeScreen(
                            onCreatePromptClick = { currentScreen = "task" },
                            onProjectsClick = { currentScreen = "projects" },
                            onLibrariesClick = { currentScreen = "libraries" },
                            onSettingsClick = { currentScreen = "settings" }
                        )
                        "projects" -> ProjectsScreen(
                            projects = projectsList,
                            onDeleteProject = { id -> projectsList.removeAll { it.id == id } },
                            onBackClick = { currentScreen = "home" }
                        )
                        "libraries" -> LibrariesScreen(
                            customItems = customLibraries,
                            onAddCustomItem = { item -> customLibraries.add(item) },
                            onBackClick = { currentScreen = "home" }
                        )
                        "settings" -> SettingsScreen(
                            onBackClick = { currentScreen = "home" }
                        )
                        "task" -> TaskScreen(
                            onBackClick = { currentScreen = "home" },
                            onNextClick = { currentScreen = "style" }
                        )
                        "style" -> StyleScreen(
                            onBackClick = { currentScreen = "task" },
                            onNextClick = { currentScreen = "composition_light" }
                        )
                        "composition_light" -> CompositionAndLightScreen(
                            onBackClick = { currentScreen = "style" },
                            onNextClick = { currentScreen = "palette" }
                        )
                        "palette" -> PaletteScreen(
                            customPalettes = customPalettes,
                            onAddCustomPalette = { palette -> customPalettes.add(palette) },
                            onBackClick = { currentScreen = "composition_light" },
                            onNextClick = { currentScreen = "final_prompt" }
                        )
                        "final_prompt" -> FinalPromptScreen(
                            onBackClick = { currentScreen = "palette" },
                            onSaveClick = { promptText ->
                                val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
                                val newProject = PromptProject(
                                    title = "Промт от " + dateFormat.format(Date()),
                                    content = promptText,
                                    date = dateFormat.format(Date())
                                )
                                projectsList.add(0, newProject)
                                currentScreen = "projects"
                            }
                        )
                    }
                }
            }
        }
    }
}
