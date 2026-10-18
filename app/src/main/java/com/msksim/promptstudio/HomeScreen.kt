package com.msksim.promptstudio

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onCreatePromptClick: () -> Unit = {},
    onProjectsClick: () -> Unit = {},
    onLibrariesClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {}
) {
    var selectedAi by remember { mutableStateOf("ChatGPT Images") }
    var selectedTab by remember { mutableStateOf(0) }

    val darkBackground = Color(0xFF0F172A)
    val cardBackground = Color(0xFF1E293B)
    val accentBlue = Color(0xFF2563EB)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Prompt Studio 2",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "by Mksim Strtogiy",
                            fontSize = 10.sp,
                            color = Color.Gray
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Menu, contentDescription = "Меню", tint = Color.White)
                    }
                },
                actions = {
                    IconButton(onClick = onSettingsClick) {
                        Icon(Icons.Default.Settings, contentDescription = "Настройки", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = darkBackground)
            )
        },
        bottomBar = {
            NavigationBar(containerColor = cardBackground) {
                val navItems = listOf(
                    Triple(0, "Главная", Icons.Default.Home),
                    Triple(1, "Проекты", Icons.Default.Folder),
                    Triple(2, "Библиотеки", Icons.Default.MenuBook),
                    Triple(3, "Профиль", Icons.Default.Person)
                )

                navItems.forEach { (index, label, icon) ->
                    NavigationBarItem(
                        selected = selectedTab == index,
                        onClick = {
                            selectedTab = index
                            when (index) {
                                1 -> onProjectsClick()
                                2 -> onLibrariesClick()
                                3 -> onSettingsClick()
                            }
                        },
                        icon = { Icon(icon, contentDescription = label) },
                        label = { Text(label) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = accentBlue,
                            selectedTextColor = accentBlue,
                            unselectedIconColor = Color.Gray,
                            unselectedTextColor = Color.Gray,
                            indicatorColor = Color.Transparent
                        )
                    )
                }
            }
        },
        containerColor = darkBackground
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text(
                text = "Выберите профиль нейросети",
                color = Color.White,
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            val aiList = listOf("ChatGPT Images", "Midjourney", "Nano Banana", "Flux")
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.height(110.dp)
            ) {
                items(aiList.size) { index ->
                    val aiName = aiList[index]
                    val isSelected = selectedAi == aiName
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) accentBlue.copy(alpha = 0.3f) else cardBackground
                        ),
                        modifier = Modifier
                            .clickable { selectedAi = aiName }
                            .height(48.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 12.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = if (isSelected) accentBlue else Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = aiName,
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (selectedAi == "Универсальный") accentBlue.copy(alpha = 0.3f) else cardBackground
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clickable { selectedAi = "Универсальный" }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp)
                ) {
                    Icon(Icons.Default.Public, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Универсальный", color = Color.White, fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            DashboardMenuItem(
                title = "Создать новый промпт",
                subtitle = "7 блоков • визуальные библиотеки",
                icon = Icons.Default.AddCircle,
                isPrimary = true,
                onClick = onCreatePromptClick
            )

            Spacer(modifier = Modifier.height(12.dp))

            DashboardMenuItem(
                title = "Мои проекты",
                subtitle = "Сохраненные промпты",
                icon = Icons.Default.Folder,
                onClick = onProjectsClick
            )

            Spacer(modifier = Modifier.height(12.dp))

            DashboardMenuItem(
                title = "Библиотеки",
                subtitle = "Художники, стили, цвета и др.",
                icon = Icons.Default.MenuBook,
                onClick = onLibrariesClick
            )

            Spacer(modifier = Modifier.height(12.dp))

            DashboardMenuItem(
                title = "Настройки",
                subtitle = "Язык, перевод, экспорт",
                icon = Icons.Default.Settings,
                onClick = onSettingsClick
            )
        }
    }
}

@Composable
fun DashboardMenuItem(
    title: String,
    subtitle: String,
    icon: ImageVector,
    isPrimary: Boolean = false,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isPrimary) Color(0xFF2563EB) else Color(0xFF1E293B)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = subtitle,
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 12.sp
                )
            }
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = Color.White.copy(alpha = 0.5f)
            )
        }
    }
}
