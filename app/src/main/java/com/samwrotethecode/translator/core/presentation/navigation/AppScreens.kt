package com.samwrotethecode.translator.core.presentation.navigation

sealed class AppScreens(val route: String) {
    object HomeScreen : AppScreens("home_screen")
    object HistoryScreen : AppScreens("history_screen")
    object SettingsScreen : AppScreens("settings_screen")
}