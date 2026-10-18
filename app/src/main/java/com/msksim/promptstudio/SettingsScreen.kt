package com.msksim.promptstudio

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBackClick: () -> Unit = {}
) {
    val context = LocalContext.current
    var selectedLanguage by remember { mutableStateOf("Русский") }
    var selectedAiApi by remember { mutableStateOf("ChatGPT Images (API)") }
    var autoCopy by remember { mutableStateOf(true) }
    var showProfileDialog by remember { mutableStateOf(false) }
    
    var userName by remember { mutableStateOf("Strogiy Mksim") }
    var userEmail by remember { mutableStateOf("strogiy.maksim@promptstudio.com") }

    val darkBackground = Color(0xFF0F172A)
    val cardBackground = Color(0xFF1E293B)
    val accentBlue = Color(0xFF2563EB)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Настройки и профиль", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад", tint = Color.White)
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Карточка профиля / авторизации
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = cardBackground),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showProfileDialog = true }
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(accentBlue, RoundedCornerShape(24.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Person, contentDescription = null, tint = Color.White, modifier = Modifier.size(28.dp))
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = userName, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        Text(text = userEmail, color = Color.Gray, fontSize = 12.sp)
                    }
                    Icon(Icons.Default.Edit, contentDescription = "Редактировать", tint = Color.Gray, modifier = Modifier.size(20.dp))
                }
            }

            // Настройки языка
            SettingSectionHeader("Язык интерфейса")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                listOf("Русский", "English").forEach { lang ->
                    val isSelected = selectedLanguage == lang
                    Button(
                        onClick = {
                            selectedLanguage = lang
                            Toast.makeText(context, "Язык изменен: $lang", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(42.dp),
                        shape = RoundedCornerShape(21.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isSelected) accentBlue else cardBackground
                        )
                    ) {
                        Text(lang, color = Color.White, fontSize = 13.sp)
                    }
                }
            }

            // Интеграция нейросетей
            SettingSectionHeader("Подключение нейросетей (API / Профили)")
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = cardBackground),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("ChatGPT Images", "Midjourney v6", "Flux.1 Pro", "Stable Diffusion XL").forEach { ai ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    selectedAiApi = ai
                                    Toast.makeText(context, "Основная нейросеть: $ai", Toast.LENGTH_SHORT).show()
                                }
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = accentBlue, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(ai, color = Color.White, fontSize = 14.sp)
                            }
                            RadioButton(
                                selected = selectedAiApi.contains(ai),
                                onClick = { selectedAiApi = ai },
                                colors = RadioButtonDefaults.colors(selectedColor = accentBlue)
                            )
                        }
                    }
                }
            }

            // Дополнительные параметры
            SettingSectionHeader("Параметры работы")
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = cardBackground),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Автокопирование промпта", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        Text("Копировать в буфер при создании", color = Color.Gray, fontSize = 11.sp)
                    }
                    Switch(
                        checked = autoCopy,
                        onCheckedChange = { autoCopy = it },
                        colors = SwitchDefaults.colors(checkedThumbColor = accentBlue)
                    )
                }
            }
        }

        // Диалог редактирования профиля / регистрации
        if (showProfileDialog) {
            AlertDialog(
                onDismissRequest = { showProfileDialog = false },
                containerColor = cardBackground,
                title = { Text("Профиль пользователя", color = Color.White, fontSize = 16.sp) },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        OutlinedTextField(
                            value = userName,
                            onValueChange = { userName = it },
                            label = { Text("Имя / Никнейм", color = Color.Gray) },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = accentBlue,
                                unfocusedBorderColor = Color.Gray
                            )
                        )
                        OutlinedTextField(
                            value = userEmail,
                            onValueChange = { userEmail = it },
                            label = { Text("Email / Аккаунт", color = Color.Gray) },
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
                            showProfileDialog = false
                            Toast.makeText(context, "Профиль обновлен", Toast.LENGTH_SHORT).show()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = accentBlue)
                    ) {
                        Text("Сохранить")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showProfileDialog = false }) {
                        Text("Закрыть", color = Color.Gray)
                    }
                }
            )
        }
    }
}

@Composable
fun SettingSectionHeader(title: String) {
    Text(
        text = title,
        color = Color.Gray,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(top = 4.dp, bottom = 2.dp)
    )
}
