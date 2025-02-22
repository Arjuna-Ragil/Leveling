package com.example.leveling.main

import androidx.compose.ui.graphics.vector.ImageVector

data class NavbarItem(
    val title: String,
    val route: String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
    val hasNews: Boolean,
    val badgeCount: Int? = null
)