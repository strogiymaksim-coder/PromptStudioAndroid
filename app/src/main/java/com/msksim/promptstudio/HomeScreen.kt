@Composable
fun HomeScreen(
    onCreatePromptClick: () -> Unit = {},
    onProjectsClick: () -> Unit = {},
    onLibrariesClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {}
) {
    // ... (остальной код экрана)
    
            // Меню быстрой навигации к конструктору
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
