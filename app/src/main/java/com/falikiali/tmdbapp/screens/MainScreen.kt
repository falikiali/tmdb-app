package com.falikiali.tmdbapp.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.falikiali.tmdbapp.BottomNavGraph
import com.falikiali.tmdbapp.component.BottomNavBar
import com.falikiali.tmdbapp.component.TopBar

@Composable
fun MainScreen(navController: NavHostController) {
    val bottomNavController = rememberNavController()

    Scaffold(
        topBar = {
            TopBar(navController = navController)
        },
        bottomBar = {
            BottomNavBar(navController = bottomNavController)
        }
    ) { paddingValues ->
        BottomNavGraph(
            bottomNavController = bottomNavController,
            navController = navController,
            modifier = Modifier
                .padding(paddingValues)
        )
    }
}