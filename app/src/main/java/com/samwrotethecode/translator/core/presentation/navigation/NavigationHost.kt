package com.samwrotethecode.translator.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.samwrotethecode.translator.history_screen.presentation.HistoryScreen
import com.samwrotethecode.translator.home_screen.presentation.HomeScreen
import androidx.navigation.NavHostController
import com.samwrotethecode.translator.dictionary_screen.presentation.DictionaryScreen

@Composable
fun NavigationHost(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = AppScreens.HomeScreen.route,
    ) {
        composable(AppScreens.HomeScreen.route) {
            HomeScreen()
        }
        composable(AppScreens.HistoryScreen.route) {
            HistoryScreen()
        }
        composable(AppScreens.DictionaryScreen.route) {
            DictionaryScreen()
        }
    }
}