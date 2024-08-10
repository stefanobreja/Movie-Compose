package com.obi.moviecompose.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.obi.moviecompose.R
import com.obi.moviecompose.presentation.favorites.FavoritesScreen
import com.obi.moviecompose.presentation.details.MovieDetailsScreen
import com.obi.moviecompose.presentation.home.HomeScreen
import com.obi.moviecompose.presentation.search.SearchScreen
import com.obi.moviecompose.ui.theme.MovieComposeAppTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        setContent {
            MovieComposeAppTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val navIconState = remember { mutableStateOf(false) }
                val topAppBarState = remember { mutableStateOf(AppBarState()) }
                val bottomBarState = rememberSaveable { mutableStateOf(true) }

                when (navBackStackEntry?.destination?.route) {
                    Screen.Home.route -> bottomBarState.value = true
                    Screen.Favorites.route -> bottomBarState.value = true
                    Screen.Search.route -> bottomBarState.value = true
                    Screen.Details.route -> bottomBarState.value = false
                    else -> bottomBarState.value = true
                }

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                topAppBarState.value.title
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
                                if (navIconState.value) {
                                    IconButton(onClick = { navController.popBackStack() }) {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                            contentDescription = "Back button"
                                        )
                                    }
                                }
                            },

                            actions = {
                                topAppBarState.value.actions?.invoke(this)
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
                            navIconState.value = false
                            bottomBarState.value = true
                            FavoritesScreen(
                                navController = navController,
                                setAppBarState = { topAppBarState.value = it }
                            )
                        }

                        composable(Screen.Home.route) {
                            navIconState.value = false
                            bottomBarState.value = true
                            HomeScreen(
                                navController = navController,
                                setAppBarState = { topAppBarState.value = it })
                        }
                        composable(Screen.Search.route) {
                            navIconState.value = false
                            bottomBarState.value = true
                            SearchScreen(
                                navController = navController,
                                setAppBarState = { topAppBarState.value = it })
                        }

                        composable(
                            "${Screen.Details.route}?movieId={movieId}",
                            arguments = listOf(navArgument(name = "movieId") {
                                type = NavType.IntType
                                defaultValue = -1
                            })
                        ) {
                            navIconState.value = true
                            bottomBarState.value = false
                            val movieId = it.arguments?.getInt("movieId")
                            movieId?.let {
                                MovieDetailsScreen(
                                    movieId = it,
                                    setAppBarState = { topAppBarState.value = it })
                            }
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

data class AppBarState(
    val title: String = "Watched",
    val actions: (@Composable RowScope.() -> Unit)? = null
)