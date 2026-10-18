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
fun CompositionAndLightScreen(
    onBackClick: () -> Unit = {},
    onNextClick: () -> Unit = {}
) {
    var selectedComposition by remember { mutableStateOf("Крупный план") }
    
    val darkBackground = Color(0xFF0F172A)
    val cardBackground = Color(0xFF1E293B)
    val accentBlue = Color(0xFF2563EB)

    val options = listOf("Крупный план", "Студийный свет", "Вид сверху", "Золотой час", "Макро", "Неоновый свет")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("4. Композиция и Свет", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) { Icon(Icons.Default.ArrowBack, contentDescription = null, tint = Color.White) }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = darkBackground)
            )
        },
        containerColor = darkBackground
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(options.size) { index ->
                    val title = options[index]
                    val isSelected = selectedComposition == title
                    Box(
                        modifier = Modifier
                            .height(100.dp)
                            .background(cardBackground, RoundedCornerShape(12.dp))
                            .border(if (isSelected) 2.dp else 0.dp, if (isSelected) accentBlue else Color.Transparent, RoundedCornerShape(12.dp))
                            .clickable { selectedComposition = title }
                            .padding(12.dp),
                        contentAlignment = Alignment.BottomStart
                    ) {
                        Text(text = title, color = Color.White, fontSize = 14.sp)
                    }
                }
            }

            Button(
                onClick = onNextClick,
                colors = ButtonDefaults.buttonColors(containerColor = accentBlue),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Далее", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(Icons.Default.ArrowForward, contentDescription = null, tint = Color.White)
                }
            }
        }
    }
}
