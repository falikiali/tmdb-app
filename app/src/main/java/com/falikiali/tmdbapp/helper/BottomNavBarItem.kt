package com.falikiali.tmdbapp.helper

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavBarItem(
    val route: String,
    val title: String,
    val iconSelected: ImageVector,
    val iconUnselected: ImageVector
)
