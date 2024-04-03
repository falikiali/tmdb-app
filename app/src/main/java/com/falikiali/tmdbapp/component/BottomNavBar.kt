package com.falikiali.tmdbapp.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.falikiali.tmdbapp.helper.BottomNavBarItem

@Composable
fun BottomNavBar(navController: NavHostController) {
    val bottomNavBarItem = listOf(
        BottomNavBarItem(
            route = "home",
            title = "Home",
            iconSelected = Icons.Filled.Home,
            iconUnselected = Icons.Outlined.Home
        ),
        BottomNavBarItem(
            route = "search",
            title = "Search",
            iconSelected = Icons.Filled.Search,
            iconUnselected = Icons.Outlined.Search
        ),
        BottomNavBarItem(
            route = "bookmarks",
            title = "Bookmarks",
            iconSelected = Icons.Filled.Bookmarks,
            iconUnselected = Icons.Outlined.Bookmarks
        )
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        bottomNavBarItem.forEach { item ->
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                onClick = {
                    if (currentDestination?.hierarchy?.any { it.route == item.route } == false) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = if (currentDestination?.hierarchy?.any { it.route == item.route } == true) item.iconSelected else item.iconUnselected,
                        contentDescription = item.title
                    )
                },
                label = {
                    Text(
                        text = item.title
                    )
                },
                alwaysShowLabel = false,
            )
        }
    }
}