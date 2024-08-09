package com.obi.moviecompose.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.obi.moviecompose.R
import com.obi.moviecompose.presentation.details.FavoritesScreen
import com.obi.moviecompose.presentation.details.MovieDetailsScreen
import com.obi.moviecompose.presentation.home.HomeScreen
import com.obi.moviecompose.presentation.search.SearchScreen
import com.obi.moviecompose.ui.theme.MovieComposeAppTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MovieComposeAppTheme {
                var showMenu by remember { mutableStateOf(false) }
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val bottomBarState = rememberSaveable { mutableStateOf(true) }
                val topFilterState = rememberSaveable { mutableStateOf(false) }
                var filterAction: FilterAction by rememberSaveable { mutableStateOf(FilterAction.NONE) }

                when (navBackStackEntry?.destination?.route) {
                    Screen.Home.route -> {
                        bottomBarState.value = true
                        topFilterState.value = true
                    }

                    Screen.Favorites.route -> {
                        bottomBarState.value = true
                        topFilterState.value = false
                    }

                    Screen.Search.route -> {
                        bottomBarState.value = true
                        topFilterState.value = false
                    }

                    Screen.Details.route -> {
                        bottomBarState.value = false
                        topFilterState.value = false
                    }

                    else -> {
                        bottomBarState.value = true
                        topFilterState.value = false
                    }
                }
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    stringResource(id = R.string.app_name),
                                    modifier = Modifier.padding(
                                        start = 24.dp,
                                        top = 8.dp,
                                        bottom = 8.dp
                                    )
                                )
                            },
                            navigationIcon = {
                                IconButton(onClick = { navController.popBackStack() }) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = "Back button"
                                    )
                                }
                            },
                            actions = {
                                IconButton(
                                    onClick = { showMenu = true }) {
                                    Icon(Icons.Filled.MoreVert, null)

                                }
                                DropdownMenu(
                                    expanded = showMenu,
                                    onDismissRequest = { showMenu = false }
                                ) {
                                    DropdownMenuItem(
                                        text = { Text(text = "Filter by rating ascending") },
                                        onClick = {
                                            showMenu = false
                                            filterAction = FilterAction.BY_RATING_ASCENDING
                                        })
                                    DropdownMenuItem(
                                        text = { Text(text = "Filter by rating descending") },
                                        onClick = {
                                            showMenu = false
                                            filterAction = FilterAction.BY_RATING_DESCENDING
                                        })
                                    DropdownMenuItem(
                                        text = { Text(text = "Filter by date ascending") },
                                        onClick = {
                                            showMenu = false
                                            filterAction = FilterAction.BY_DATE_ASCENDING
                                        })
                                    DropdownMenuItem(
                                        text = { Text(text = "Filter by date descending") },
                                        onClick = {
                                            showMenu = false
                                            filterAction = FilterAction.BY_DATE_DESCENDING
                                        })
                                    if (filterAction != FilterAction.NONE) {
                                        DropdownMenuItem(
                                            text = { Text(text = "Reset") },
                                            onClick = {
                                                showMenu = false
                                                filterAction = FilterAction.NONE
                                            })
                                    }

                                }
                            }
                        )
                    },
                    bottomBar = {
                        BottomNavigation(navController, bottomBarState.value)
                    }) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Home.route,
                        Modifier.padding(innerPadding)
                    ) {
                        composable(Screen.Favorites.route) {
                            bottomBarState.value = true
                            FavoritesScreen(navController = navController)
                        }

                        composable(Screen.Home.route) {
                            bottomBarState.value = true
                            HomeScreen(navController = navController, filterAction = filterAction)
                        }
                        composable(Screen.Search.route) {
                            bottomBarState.value = true
                            SearchScreen(navController = navController)
                        }

                        composable(
                            "${Screen.Details.route}?movieId={movieId}",
                            arguments = listOf(navArgument(name = "movieId") {
                                type = NavType.IntType
                                defaultValue = -1
                            })
                        ) {
                            bottomBarState.value = false
                            val movieId = it.arguments?.getInt("movieId")
                            movieId?.let { MovieDetailsScreen(movieId = it) }
                        }
                    }

                }

            }
        }
    }

    @Composable
    private fun BottomNavigation(
        navController: NavHostController,
        isVisible: Boolean
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val screens =
            listOf(Screen.Home, Screen.Favorites, Screen.Search)

        AnimatedVisibility(
            visible = isVisible,
            enter = slideInVertically(animationSpec = tween(500), initialOffsetY = { it }),
            exit = slideOutVertically(animationSpec = tween(500), targetOffsetY = { it })
        ) {
            NavigationBar {
                val currentRoute = navBackStackEntry?.destination?.route
                screens.forEach { screen ->
                    NavigationBarItem(
                        selected = currentRoute == screen.route,
                        alwaysShowLabel = false,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            screen.icon?.let {
                                Icon(
                                    imageVector = it,
                                    contentDescription = screen.title
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}

enum class FilterAction {
    NONE,
    BY_RATING_ASCENDING,
    BY_RATING_DESCENDING,
    BY_DATE_ASCENDING,
    BY_DATE_DESCENDING,
}