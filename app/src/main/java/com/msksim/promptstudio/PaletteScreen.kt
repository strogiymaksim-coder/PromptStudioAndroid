package com.msksim.promptstudio

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class ColorPalette(
    val name: String,
    val colors: List<Color>,
    val hexCodes: List<String>
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaletteScreen(
    customPalettes: List<ColorPalette>,
    onAddCustomPalette: (ColorPalette) -> Unit,
    onBackClick: () -> Unit = {},
    onNextClick: () -> Unit = {}
) {
    var selectedTab by remember { mutableStateOf(0) } // 0: Готовые, 1: Мои
    var showAddDialog by remember { mutableStateOf(false) }

    // Поля для создания своей палитры
    var newPaletteName by remember { mutableStateOf("") }
    var newHex1 by remember { mutableStateOf("#2563EB") }
    var newHex2 by remember { mutableStateOf("#1E293B") }
    var newHex3 by remember { mutableStateOf("#F8FAFC") }

    val defaultPalettes = listOf(
        ColorPalette(
            "Тёплая светлая",
            listOf(Color(0xFFE6C79C), Color(0xFFF5E6D3), Color(0xFFD49B6A)),
            listOf("#E6C79C", "#F5E6D3", "#D49B6A")
        ),
        ColorPalette(
            "Глубокая синяя и золотая",
            listOf(Color(0xFF1E3A8A), Color(0xFFD97706), Color(0xFF0F172A)),
            listOf("#1E3A8A", "#D97706", "#0F172A")
        ),
        ColorPalette(
            "Киберпанк / Неон",
            listOf(Color(0xFF7C3AED), Color(0xFFEC4899), Color(0xFF06B6D4)),
            listOf("#7C3AED", "#EC4899", "#06B6D4")
        ),
        ColorPalette(
            "Природная зелёная",
            listOf(Color(0xFF15803D), Color(0xFF86EFAC), Color(0xFF14532D)),
            listOf("#15803D", "#86EFAC", "#14532D")
        ),
        ColorPalette(
            "Монохромный нуар",
            listOf(Color(0xFF111827), Color(0xFF4B5563), Color(0xFFE5E7EB)),
            listOf("#111827", "#4B5563", "#E5E7EB")
        ),
        ColorPalette(
            "Корпоративная",
            listOf(Color(0xFF0284C7), Color(0xFFBAE6FD), Color(0xFF0369A1)),
            listOf("#0284C7", "#BAE6FD", "#0369A1")
        ),
        ColorPalette(
            "Винтаж / Ретро",
            listOf(Color(0xFF9A3412), Color(0xFFD97706), Color(0xFFFEF3C7)),
            listOf("#9A3412", "#D97706", "#FEF3C7")
        ),
        ColorPalette(
            "Пастельная мягкая",
            listOf(Color(0xFFFBCFE8), Color(0xFFBAE6FD), Color(0xFFFED7AA)),
            listOf("#FBCFE8", "#BAE6FD", "#FED7AA")
        )
    )

    val displayedPalettes = if (selectedTab == 0) defaultPalettes else (defaultPalettes.take(0) + customPalettes)
    var selectedPalette by remember { mutableStateOf(defaultPalettes[0]) }

    val darkBackground = Color(0xFF0F172A)
    val cardBackground = Color(0xFF1E293B)
    val accentBlue = Color(0xFF2563EB)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("6. Цветовая палитра", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад", tint = Color.White)
                    }
                },
                actions = {
                    if (selectedTab == 1) {
                        IconButton(onClick = { showAddDialog = true }) {
                            Icon(Icons.Default.Add, contentDescription = "Создать палитру", tint = Color.White)
                        }
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
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                TabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = cardBackground,
                    contentColor = Color.White,
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    Tab(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        text = { Text("Готовые палитры", fontSize = 13.sp) }
                    )
                    Tab(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        text = { Text("Мои (${customPalettes.size})", fontSize = 13.sp) }
                    )
                }

                if (selectedTab == 1 && customPalettes.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.Palette, contentDescription = null, tint = accentBlue, modifier = Modifier.size(48.dp))
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("У вас пока нет своих палитр", color = Color.Gray, fontSize = 13.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(
                                onClick = { showAddDialog = true },
                                colors = ButtonDefaults.buttonColors(containerColor = accentBlue)
                            ) {
                                Text("Создать палитру")
                            }
                        }
                    }
                } else {
                    // Сетка палитр
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.height(240.dp)
                    ) {
                        items(displayedPalettes.size) { index ->
                            val item = displayedPalettes[index]
                            val isSelected = selectedPalette.name == item.name

                            Card(
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(containerColor = cardBackground),
                                modifier = Modifier
                                    .height(85.dp)
                                    .border(
                                        width = if (isSelected) 2.dp else 0.dp,
                                        color = if (isSelected) accentBlue else Color.Transparent,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .clickable { selectedPalette = item }
                            ) {
                                Column(
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .fillMaxSize(),
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = item.name,
                                        color = Color.White,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium,
                                        maxLines = 1
                                    )
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(24.dp),
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        item.colors.forEach { color ->
                                            Box(
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .fillMaxHeight()
                                                    .background(color, RoundedCornerShape(4.dp))
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Карточка информации о выбранной палитре
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = cardBackground),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "Выбрано: ${selectedPalette.name}",
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.height(28.dp),
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                selectedPalette.colors.forEach { color ->
                                    Box(
                                        modifier = Modifier
                                            .size(28.dp)
                                            .background(color, RoundedCornerShape(6.dp))
                                    )
                                }
                            }
                            Text(
                                text = selectedPalette.hexCodes.joinToString("  "),
                                color = Color.Gray,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Button(
                onClick = onNextClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(containerColor = accentBlue)
            ) {
                Text("Далее: Формат", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.Default.ArrowForward, contentDescription = null, tint = Color.White)
            }
        }

        // Диалог создания своей палитры
        if (showAddDialog) {
            AlertDialog(
                onDismissRequest = { showAddDialog = false },
                containerColor = cardBackground,
                title = { Text("Новая цветовая палитра", color = Color.White, fontSize = 16.sp) },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        OutlinedTextField(
                            value = newPaletteName,
                            onValueChange = { newPaletteName = it },
                            placeholder = { Text("Название палитры", color = Color.Gray) },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = accentBlue,
                                unfocusedBorderColor = Color.Gray
                            )
                        )
                        OutlinedTextField(
                            value = newHex1,
                            onValueChange = { newHex1 = it },
                            placeholder = { Text("Цвет 1 (HEX, например #FF5733)", color = Color.Gray) },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = accentBlue,
                                unfocusedBorderColor = Color.Gray
                            )
                        )
                        OutlinedTextField(
                            value = newHex2,
                            onValueChange = { newHex2 = it },
                            placeholder = { Text("Цвет 2 (HEX)", color = Color.Gray) },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = accentBlue,
                                unfocusedBorderColor = Color.Gray
                            )
                        )
                        OutlinedTextField(
                            value = newHex3,
                            onValueChange = { newHex3 = it },
                            placeholder = { Text("Цвет 3 (HEX)", color = Color.Gray) },
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
                            if (newPaletteName.isNotBlank()) {
                                try {
                                    val c1 = Color(android.graphics.Color.parseColor(newHex1))
                                    val c2 = Color(android.graphics.Color.parseColor(newHex2))
                                    val c3 = Color(android.graphics.Color.parseColor(newHex3))

                                    val custom = ColorPalette(
                                        name = newPaletteName,
                                        colors = listOf(c1, c2, c3),
                                        hexCodes = listOf(newHex1, newHex2, newHex3)
                                    )
                                    onAddCustomPalette(custom)
                                    showAddDialog = false
                                    selectedTab = 1
                                } catch (e: Exception) {
                                    // Ошибка парсинга HEX кодов, можно проигнорировать или оставить дефолт
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = accentBlue)
                    ) {
                        Text("Сохранить")
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
