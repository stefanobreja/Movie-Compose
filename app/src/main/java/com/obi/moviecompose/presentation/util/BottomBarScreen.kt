package com.obi.moviecompose.presentation.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationDestination(open val route: String, open val title: String) {
    object MovieDetails : NavigationDestination(route = "movieDetails", title = "Movie name")
}

sealed class BottomBarScreen(
    override val route: String, override val title: String, val icon: ImageVector
) : NavigationDestination(route, title) {
    object Home : BottomBarScreen(route = "home", title = "Home", icon = Icons.Default.Home)
    object Favorites : BottomBarScreen(route = "favorites", title = "Favorites", icon = Icons.Default.Favorite)
    object Search : BottomBarScreen(route = "search", title = "Search", icon = Icons.Default.Search)
}

