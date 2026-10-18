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
                            onNextClick = {
                                // Здесь в следующих шагах добавим переход к блоку "Стиль и художники"
                            }
                        )
                    }
                }
            }
        }
    }
}
