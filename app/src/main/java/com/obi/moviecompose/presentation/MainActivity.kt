package com.obi.moviecompose.presentation

import android.content.res.Resources.Theme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.obi.moviecompose.MovieComposeApplication
import com.obi.moviecompose.R
import com.obi.moviecompose.presentation.home.HomeScreen
import com.obi.moviecompose.ui.theme.MovieComposeAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieComposeAppTheme {
                val navController = rememberNavController()
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    stringResource(id = R.string.app_name),
                                    modifier = Modifier.padding(start = 24.dp, top = 8.dp, bottom = 8.dp)
                                )
                            }
                        )
                    },
                    bottomBar = {
                        BottomNavigation(navController)
                    }) { innerPadding ->
                    NavigationGraph(navController, innerPadding)
                }

            }
        }
    }

    @Composable
    private fun BottomNavigation(
        navController: NavHostController
    ) {
        val screens = listOf(BottomBarScreen.Home, BottomBarScreen.Favorites, BottomBarScreen.Search)

        BottomNavigation() {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            screens.forEach { screen ->
                BottomNavigationItem(
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
                    icon = { Icon(imageVector = screen.icon, contentDescription = screen.title) }
                )
            }

        }
    }

    @Composable
    private fun NavigationGraph(
        navController: NavHostController,
        innerPadding: PaddingValues
    ) {
        NavHost(
            navController = navController,
            startDestination = BottomBarScreen.Home.route,
            Modifier.padding(innerPadding)
        ) {
            composable(BottomBarScreen.Home.route) {
                HomeScreen(navController = navController)
            }
            composable(BottomBarScreen.Favorites.route) {
            }
            composable(BottomBarScreen.Search.route) {
            }
        }
    }
}