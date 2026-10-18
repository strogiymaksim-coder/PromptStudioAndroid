package com.msksim.promptstudio

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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

data class OptionItem(
    val name: String,
    val description: String,
    val icon: ImageVector
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompositionAndLightScreen(
    onBackClick: () -> Unit = {},
    onNextClick: () -> Unit = {}
) {
    var selectedTab by remember { mutableStateOf(0) } // 0: Композиция, 1: Свет

    val compositions = listOf(
        OptionItem("Центральная симметрия", "Строгий фокус по центру, баланс", Icons.Default.CenterFocusStrong),
        OptionItem("Правило третей", "Классическое гармоничное смещение", Icons.Default.GridOn),
        OptionItem("Диагональная", "Динамика и движение в кадре", Icons.Default.TrendingUp),
        OptionItem("Крупный план (Macro)", "Максимальная детализация объекта", Icons.Default.ZoomIn),
        OptionItem("Аэросъёмка", "Вид сверху, панорамный обзор", Icons.Default.Map),
        OptionItem("Низкий угол (Frog's eye)", "Монументальный взгляд снизу вверх", Icons.Default.VerticalAlignBottom),
        OptionItem("Диптих / Триада", "Несколько связанных кадров в ряд", Icons.Default.ViewAgenda),
        OptionItem("Панорама 16:9", "Широкий кинематографический охват", Icons.Default.AspectRatio)
    )

    val lightTypes = listOf(
        OptionItem("Золотой час", "Мягкий теплый свет низкого солнца", Icons.Default.WbSunny),
        OptionItem("Кинематографический контровой", "Эффектная подсветка сзади, силуэт", Icons.Default.Highlight),
        OptionItem("Объёмный рассеянный (Volumetric)", "Лучи света, туман, атмосфера", Icons.Default.CloudQueue),
        OptionItem("Студийный софтбокс", "Чистое профессиональное освещение", Icons.Default.Lightbulb),
        OptionItem("Неоновый / Киберпанк", "Яркие контрастные цветные подсветки", Icons.Default.FlashOn),
        OptionItem("Мрачный нуар (Chiaroscuro)", "Глубокие тени, драматичный контраст", Icons.Default.Brightness2),
        OptionItem("Лунный свет / Ночной", "Холодные синие оттенки, таинственность", Icons.Default.NightsStay),
        OptionItem("Кольцевой свет (Ring light)", "Равномерная подсветка без жестких теней", Icons.Default.RadiobuttonUnchecked)
    )

    var selectedComposition by remember { mutableStateOf(compositions[0].name) }
    var selectedLight by remember { mutableStateOf(lightTypes[0].name) }

    val currentList = if (selectedTab == 0) compositions else lightTypes
    val currentSelected = if (selectedTab == 0) selectedComposition else selectedLight

    val darkBackground = Color(0xFF0F172A)
    val cardBackground = Color(0xFF1E293B)
    val accentBlue = Color(0xFF2563EB)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (selectedTab == 0) "4. Композиция" else "5. Свет и освещение",
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    Tab(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        text = { Text("Композиция", fontSize = 13.sp, fontWeight = FontWeight.Bold) }
                    )
                    Tab(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        text = { Text("Свет", fontSize = 13.sp, fontWeight = FontWeight.Bold) }
                    )
                }

                // Сетка вариантов с иконками и описанием
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.height(420.dp)
                ) {
                    items(currentList.size) { index ->
                        val item = currentList[index]
                        val isSelected = currentSelected == item.name

                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = cardBackground),
                            modifier = Modifier
                                .height(110.dp)
                                .border(
                                    width = if (isSelected) 2.dp else 0.dp,
                                    color = if (isSelected) accentBlue else Color.Transparent,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .clickable {
                                    if (selectedTab == 0) {
                                        selectedComposition = item.name
                                    } else {
                                        selectedLight = item.name
                                    }
                                }
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(10.dp),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = null,
                                    tint = if (isSelected) accentBlue else Color.White,
                                    modifier = Modifier.size(22.dp)
                                )
                                Column {
                                    Text(
                                        text = item.name,
                                        color = Color.White,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        maxLines = 1
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = item.description,
                                        color = Color.Gray,
                                        fontSize = 9.sp,
                                        maxLines = 2
                                    )
                                }
                            }
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
                Text("Далее", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.Default.ArrowForward, contentDescription = null, tint = Color.White)
            }
        }
    }
}
