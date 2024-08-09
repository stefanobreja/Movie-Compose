package com.obi.moviecompose.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val title: String,
    val icon: ImageVector?,
    showBottomNav: Boolean
) {
    data object Favorites : Screen(
        route = "favorites",
        title = "Favorites",
        icon = Icons.Default.Favorite,
        showBottomNav = true
    )

    data object Home :
        Screen(route = "home", title = "Home", icon = Icons.Default.Home, showBottomNav = true)

    data object Search : Screen(
        route = "search",
        title = "Search",
        icon = Icons.Default.Search,
        showBottomNav = true
    )

    data object Details :
        Screen(route = "details", title = "Details", icon = null, showBottomNav = false)
}