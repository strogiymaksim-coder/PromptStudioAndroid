package com.msksim.promptstudio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    var currentScreen by remember { mutableStateOf("welcome") }

                    when (currentScreen) {
                        "welcome" -> WelcomeScreen(
                            onStartClick = { currentScreen = "home" }
                        )
                        "home" -> HomeScreen(
                            onCreatePromptClick = { currentScreen = "task" }
                        )
                        "task" -> TaskScreen(
                            onBackClick = { currentScreen = "home" },
                            onNextClick = { currentScreen = "style" }
                        )
                        "style" -> StyleScreen(
                            onBackClick = { currentScreen = "task" },
                            onNextClick = { currentScreen = "palette" }
                        )
                        "palette" -> PaletteScreen(
                            onBackClick = { currentScreen = "style" },
                            onNextClick = { currentScreen = "composition_light" }
                        )
                        "composition_light" -> CompositionAndLightScreen(
                            onBackClick = { currentScreen = "palette" },
                            onNextClick = { currentScreen = "final_prompt" }
                        )
                        "final_prompt" -> FinalPromptScreen(
                            onBackClick = { currentScreen = "composition_light" },
                            onSaveClick = {
                                // Возврат на главный экран после сохранения
                                currentScreen = "home"
                            }
                        )
                    }
                }
            }
        }
    }
}
