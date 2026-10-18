package com.msksim.promptstudio

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Модель сохраненного проекта
data class PromptProject(
    val id: String = System.currentTimeMillis().toString(),
    val title: String,
    val content: String,
    val date: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectsScreen(
    projects: List<PromptProject>,
    onDeleteProject: (String) -> Unit,
    onBackClick: () -> Unit = {}
) {
    val context = LocalContext.current
    val darkBackground = Color(0xFF0F172A)
    val cardBackground = Color(0xFF1E293B)
    val accentBlue = Color(0xFF2563EB)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Мои проекты (${projects.size})", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White) },
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
        if (projects.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(Icons.Default.FolderOpen, contentDescription = null, tint = accentBlue, modifier = Modifier.size(64.dp))
                Spacer(modifier = Modifier.height(16.dp))
                Text("Список проектов пока пуст", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Создайте и сохраните свой первый промт", color = Color.Gray, fontSize = 13.sp)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(projects) { project ->
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = cardBackground),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = project.title,
                                    color = Color.White,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = project.date,
                                    color = Color.Gray,
                                    fontSize = 11.sp
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = project.content,
                                color = Color.White.copy(alpha = 0.8f),
                                fontSize = 12.sp,
                                maxLines = 3
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(onClick = {
                                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                    val clip = ClipData.newPlainText("Prompt", project.content)
                                    clipboard.setPrimaryClip(clip)
                                    Toast.makeText(context, "Скопировано в буфер", Toast.LENGTH_SHORT).show()
                                }) {
                                    Icon(Icons.Default.ContentCopy, contentDescription = "Копировать", tint = accentBlue, modifier = Modifier.size(20.dp))
                                }
                                IconButton(onClick = { onDeleteProject(project.id) }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Удалить", tint = Color.Red.copy(alpha = 0.7f), modifier = Modifier.size(20.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
