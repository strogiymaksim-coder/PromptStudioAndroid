package com.msksim.promptstudio

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.MoreVert
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
    onBackClick: () -> Unit = {},
    onNextClick: () -> Unit = {}
) {
    var selectedTab by remember { mutableStateOf(0) }

    val palettes = listOf(
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
            "Нейтральная",
            listOf(Color(0xFF94A3B8), Color(0xFFE2E8F0), Color(0xFF475569)),
            listOf("#94A3B8", "#E2E8F0", "#475569")
        ),
        ColorPalette(
            "Природная зелёная",
            listOf(Color(0xFF15803D), Color(0xFF86EFAC), Color(0xFF14532D)),
            listOf("#15803D", "#86EFAC", "#14532D")
        ),
        ColorPalette(
            "Фиолетовая",
            listOf(Color(0xFF7C3AED), Color(0xFFDDD6FE), Color(0xFF4C1D95)),
            listOf("#7C3AED", "#DDD6FE", "#4C1D95")
        ),
        ColorPalette(
            "Корпоративная",
            listOf(Color(0xFF0284C7), Color(0xFFBAE6FD), Color(0xFF0369A1)),
            listOf("#0284C7", "#BAE6FD", "#0369A1")
        )
    )

    var selectedPalette by remember { mutableStateOf(palettes[0]) }

    val darkBackground = Color(0xFF0F172A)
    val cardBackground = Color(0xFF1E293B)
    val accentBlue = Color(0xFF2563EB)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "5. Цветовая палитра",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
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
                        text = { Text("Мои", fontSize = 13.sp) }
                    )
                }

                // Сетка готовых палитр
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.height(260.dp)
                ) {
                    items(palettes.size) { index ->
                        val item = palettes[index]
                        val isSelected = selectedPalette.name == item.name

                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = cardBackground),
                            modifier = Modifier
                                .border(
                                    width = if (isSelected) 2.dp else 0.dp,
                                    color = if (isSelected) accentBlue else Color.Transparent,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .clickable { selectedPalette = item }
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text(
                                    text = item.name,
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    maxLines = 1
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(28.dp),
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

                Spacer(modifier = Modifier.height(16.dp))

                // Полностью восстановленная информационная карточка
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = cardBackground),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "Выбранные HEX-коды:",
                            color = Color.White.copy(alpha = 0.6f),
                            fontSize = 12.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            selectedPalette.hexCodes.forEachIndexed { i, hex ->
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(12.dp)
                                            .background(selectedPalette.colors[i], RoundedCornerShape(3.dp))
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(text = hex, color = Color.White, fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Кнопка перехода на следующий экран
            Button(
                onClick = onNextClick,
                colors = ButtonDefaults.buttonColors(containerColor = accentBlue),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "Далее", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null, tint = Color.White)
                }
            }
        }
    }
}
