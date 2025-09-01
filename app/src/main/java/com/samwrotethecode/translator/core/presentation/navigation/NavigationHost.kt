package com.samwrotethecode.translator.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.samwrotethecode.translator.home_screen.presentation.HomeScreen

@Composable
fun NavigationHost() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppScreens.HomeScreen.route,
    ) {
        composable(AppScreens.HomeScreen.route) {
            HomeScreen(navController = navController)
        }
    }
}