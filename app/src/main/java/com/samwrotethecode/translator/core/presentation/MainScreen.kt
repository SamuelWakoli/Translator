package com.samwrotethecode.translator.core.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.samwrotethecode.translator.R
import com.samwrotethecode.translator.core.presentation.navigation.AppScreens
import com.samwrotethecode.translator.core.presentation.navigation.NavigationHost

@Composable
fun MainScreen(
    windowSizeClass: WindowWidthSizeClass
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val navItems = listOf(
        NavigationItem(
            route = AppScreens.HomeScreen.route,
            icon = painterResource(R.drawable.ic_launcher_foreground),
            label = "Translator",
            isVector = false
        ),
        NavigationItem(
            route = AppScreens.DictionaryScreen.route,
            icon = Icons.Default.Book,
            label = "Dictionary"
        ),
        NavigationItem(
            route = AppScreens.HistoryScreen.route,
            icon = Icons.Default.History,
            label = "History",
        ),
    )

    val layoutType = when (windowSizeClass) {
        WindowWidthSizeClass.Expanded -> {
            NavigationSuiteType.NavigationDrawer
        }

        WindowWidthSizeClass.Medium -> {
            NavigationSuiteType.NavigationRail
        }

        else -> {
            NavigationSuiteType.NavigationBar
        }
    }

    NavigationSuiteScaffold(
        layoutType = layoutType, navigationSuiteItems = {
            navItems.forEach { item ->
                item(
                    selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                    onClick = {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        if (item.isVector) {
                            Icon(item.icon as ImageVector, contentDescription = item.label)
                        } else {
                            Image(
                                painter = item.icon as Painter,
                                modifier = Modifier.size(36.dp),
                                contentDescription = item.label,
                                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurfaceVariant)
                            )
                        }
                    },
                    label = { Text(item.label) })
            }
        }) {
        NavigationHost(
            navController = navController,
        )
    }
}

data class NavigationItem(
    val route: String, val icon: Any, val label: String, val isVector: Boolean = true
)
