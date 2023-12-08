package com.obi.moviecompose.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
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
import com.obi.moviecompose.presentation.home.HomeScreen
import com.obi.moviecompose.presentation.moviedetails.MovieDetailsScreen
import com.obi.moviecompose.presentation.util.BottomBarScreen
import com.obi.moviecompose.presentation.util.NavigationDestination
import com.obi.moviecompose.ui.theme.MovieComposeAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            MovieComposeAppTheme {
                Scaffold(
                    topBar = { TopBar(navController = navController) },
                    bottomBar = { BottomNavigation(navController) }) { innerPadding ->
                    NavigationGraph(
                        navController,
                        innerPadding
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun TopBar(modifier: Modifier = Modifier, navController: NavHostController = rememberNavController()) {
        val currentRoute = navController.currentDestination?.route
        val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

        TopAppBar(
            modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),

            title = {
                Text(
                    when (currentRoute) {
                        BottomBarScreen.Home.route -> stringResource(id = R.string.app_name)
                        else -> stringResource(id = R.string.app_name)
                    },
                    modifier = Modifier.padding(
                        start = 24.dp, top = 8.dp, bottom = 8.dp
                    )
                )
            }
        )
    }

    @Composable
    private fun BottomNavigation(
        navController: NavHostController = rememberNavController()
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
    fun NavigationGraph(
        navController: NavHostController = rememberNavController(),
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
            composable(
                "${NavigationDestination.MovieDetails.route}/{movieId}",
                arguments = listOf(navArgument("movieId") { type = NavType.IntType })
            ) { backStackEntry ->
                MovieDetailsScreen(movieId = backStackEntry.arguments?.getInt("movieId"), navController = navController)
            }
        }
    }
}