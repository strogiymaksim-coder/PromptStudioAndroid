package com.msksim.promptstudio

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Модель элемента библиотеки
data class LibraryItem(
    val id: String = System.currentTimeMillis().toString(),
    val category: String, // Стили, Художники, Свет, Композиция
    val name: String,
    val description: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibrariesScreen(
    customItems: List<LibraryItem>,
    onAddCustomItem: (LibraryItem) -> Unit,
    onBackClick: () -> Unit = {}
) {
    var selectedTab by remember { mutableStateOf(0) }
    var showAddDialog by remember { mutableStateOf(false) }
    
    var newItemName by remember { mutableStateOf("") }
    var newItemDesc by remember { mutableStateOf("") }

    val categories = listOf("Стили", "Художники", "Свет", "Композиция")
    val currentCategory = categories[selectedTab]

    val darkBackground = Color(0xFF0F172A)
    val cardBackground = Color(0xFF1E293B)
    val accentBlue = Color(0xFF2563EB)

    // Предустановленные элементы по умолчанию
    val defaultItems = mapOf(
        "Стили" to listOf(
            LibraryItem(category = "Стили", name = "Фотореализм", description = "Максимальная детализация, естественное освещение"),
            LibraryItem(category = "Стили", name = "Премиальная 3D-графика", description = "Отрендрено в Octane, объемные материалы, глянец"),
            LibraryItem(category = "Стили", name = "Кинематографический арт", description = "Кадр из фильма, анаморфный объектив, драма")
        ),
        "Художники" to listOf(
            LibraryItem(category = "Художники", name = "Greg Rutkowski", description = "Эпичное фэнтези, детализированное освещение"),
            LibraryItem(category = "Художники", name = "Makoto Shinkai", description = "Яркое аниме небо, облака, глубокая атмосфера"),
            LibraryItem(category = "Художники", name = "Beeple", description = "Цифровой сюрреализм, футуристические объекты")
        ),
        "Свет" to listOf(
            LibraryItem(category = "Свет", name = "Золотой час", description = "Мягкий теплый свет низкого солнца"),
            LibraryItem(category = "Свет", name = "Контровой свет", description = "Подсветка сзади, выразительный силуэт"),
            LibraryItem(category = "Свет", name = "Неоновый свет", description = "Яркие фиолетовые и голубые акценты, киберпанк")
        ),
        "Композиция" to listOf(
            LibraryItem(category = "Композиция", name = "Правило третей", description = "Классическое гармоничное расположение объектов"),
            LibraryItem(category = "Композиция", name = "Центральная симметрия", description = "Строгий баланс и фокус по центру"),
            LibraryItem(category = "Композиция", name = "Крупный план (Macro)", description = "Детализированный фокус на главной части")
        )
    )

    val displayedItems = (defaultItems[currentCategory] ?: emptyList()) + customItems.filter { it.category == currentCategory }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Библиотека знаний", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад", tint = Color.White)
                    }
                },
                actions = {
                    IconButton(onClick = { showAddDialog = true }) {
                        Icon(Icons.Default.Add, contentDescription = "Добавить", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = darkBackground)
            )
        },
        containerColor = darkBackground
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // Вкладки категорий
            ScrollableTabRow(
                selectedTabIndex = selectedTab,
                containerColor = cardBackground,
                contentColor = Color.White,
                edgePadding = 0.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                categories.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title, fontSize = 13.sp) }
                    )
                }
            }

            // Список элементов
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(displayedItems) { item ->
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = cardBackground),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(
                                text = item.name,
                                color = Color.White,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = item.description,
                                color = Color.Gray,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }

        // Диалог добавления своего элемента в библиотеку
        if (showAddDialog) {
            AlertDialog(
                onDismissRequest = { showAddDialog = false },
                containerColor = cardBackground,
                title = { Text("Добавить в «$currentCategory»", color = Color.White, fontSize = 16.sp) },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        OutlinedTextField(
                            value = newItemName,
                            onValueChange = { newItemName = it },
                            placeholder = { Text("Название", color = Color.Gray) },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = accentBlue,
                                unfocusedBorderColor = Color.Gray
                            )
                        )
                        OutlinedTextField(
                            value = newItemDesc,
                            onValueChange = { newItemDesc = it },
                            placeholder = { Text("Описание / Особенности", color = Color.Gray) },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = accentBlue,
                                unfocusedBorderColor = Color.Gray
                            )
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (newItemName.isNotBlank()) {
                                onAddCustomItem(
                                    LibraryItem(
                                        category = currentCategory,
                                        name = newItemName,
                                        description = newItemDesc.ifBlank { "Пользовательский элемент" }
                                    )
                                )
                                newItemName = ""
                                newItemDesc = ""
                                showAddDialog = false
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = accentBlue)
                    ) {
                        Text("Добавить")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showAddDialog = false }) {
                        Text("Отмена", color = Color.Gray)
                    }
                }
            )
        }
    }
}
