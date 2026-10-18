package com.msksim.promptstudio

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinalPromptScreen(
    onBackClick: () -> Unit = {},
    onSaveClick: () -> Unit = {}
) {
    var selectedLanguage by remember { mutableStateOf(0) } // 0: Русский, 1: English
    var selectedFormat by remember { mutableStateOf("A4 Вертикальный") }
    var restrictionsText by remember { mutableStateOf("") }
    var step by remember { mutableStateOf(7) } // 7: Формат, 8: Готовый промпт

    val darkBackground = Color(0xFF0F172A)
    val cardBackground = Color(0xFF1E293B)
    val accentBlue = Color(0xFF2563EB)

    val formats = listOf("A4 Вертикальный", "A4 Горизонтальный", "16:9 (горизонтальный)", "1:1 Квадрат")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (step == 7) "7. Формат и ограничения" else "Готовый промпт",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        if (step == 8) step = 7 else onBackClick()
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад", tint = Color.White)
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Меню", tint = Color.White)
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
            if (step == 7) {
                // Шаг 7: Формат и ограничения
                Column {
                    Text(
                        text = "Формат",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        formats.chunked(2).forEach { rowFormats ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                rowFormats.forEach { format ->
                                    val isSelected = selectedFormat == format
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(70.dp)
                                            .background(cardBackground, RoundedCornerShape(12.dp))
                                            .border(
                                                width = if (isSelected) 2.dp else 0.dp,
                                                color = if (isSelected) accentBlue else Color.Transparent,
                                                shape = RoundedCornerShape(12.dp)
                                            )
                                            .clickable { selectedFormat = format }
                                            .padding(12.dp),
                                        contentAlignment = Alignment.CenterStart
                                    ) {
                                        Text(
                                            text = format,
                                            color = Color.White,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "Ограничения (необязательно)",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    OutlinedTextField(
                        value = restrictionsText,
                        onValueChange = { restrictionsText = it },
                        placeholder = {
                            Text(
                                "Например: без надписей, без логотипов, без лишних предметов...",
                                color = Color.Gray,
                                fontSize = 12.sp
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = cardBackground,
                            unfocusedContainerColor = cardBackground,
                            focusedBorderColor = accentBlue,
                            unfocusedBorderColor = Color.Transparent,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        )
                    )
                }

                Button(
                    onClick = { step = 8 },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = accentBlue)
                ) {
                    Text("Создать промпт", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                }

            } else {
                // Шаг 8: Готовый промпт
                Column(modifier = Modifier.weight(1f)) {
                    // Переключатель языка
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(cardBackground, RoundedCornerShape(24.dp))
                            .padding(4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = { selectedLanguage = 0 },
                            modifier = Modifier
                                .weight(1f)
                                .height(36.dp),
                            shape = RoundedCornerShape(20.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (selectedLanguage == 0) accentBlue else Color.Transparent
                            )
                        ) {
                            Text("Русский", color = Color.White, fontSize = 12.sp)
                        }
                        Button(
                            onClick = { selectedLanguage = 1 },
                            modifier = Modifier
                                .weight(1f)
                                .height(36.dp),
                            shape = RoundedCornerShape(20.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (selectedLanguage == 1) accentBlue else Color.Transparent
                            )
                        ) {
                            Text("English", color = Color.White, fontSize = 12.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Карточка сгенерированного промпта
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = cardBackground),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text(
                                text = "Задача и назначение:\nОбложка корпоративного журнала\nСюжет и объекты:\nСемья из трёх поколений, уютный дом, природа, тепло, гармония",
                                color = Color.White.copy(alpha = 0.9f),
                                fontSize = 13.sp,
                                lineHeight = 18.sp
                            )
                            Text(
                                text = "Стиль и художники:\nПремиальная 3D-графика, современный стиль, выразительные материалы",
                                color = Color.White.copy(alpha = 0.9f),
                                fontSize = 13.sp,
                                lineHeight = 18.sp
                            )
                            Text(
                                text = "Формат:\n$selectedFormat",
                                color = Color.White.copy(alpha = 0.9f),
                                fontSize = 13.sp,
                                lineHeight = 18.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Кнопки действий (Копировать / Сохранить)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = {},
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
                    ) {
                        Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Копировать", fontSize = 13.sp)
                    }

                    Button(
                        onClick = onSaveClick,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = accentBlue)
                    ) {
                        Icon(Icons.Default.Save, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Сохранить", fontSize = 13.sp)
                    }
                }
            }
        }
    }
}
