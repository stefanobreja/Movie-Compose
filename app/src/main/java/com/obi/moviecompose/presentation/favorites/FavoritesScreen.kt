package com.obi.moviecompose.presentation.favorites

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.obi.moviecompose.R
import com.obi.moviecompose.presentation.AppBarState
import com.obi.moviecompose.presentation.Screen
import com.obi.moviecompose.presentation.components.MoviesGrid
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel = koinViewModel(),
    navController: NavController,
    setAppBarState: (AppBarState) -> Unit
) {
    val movies by viewModel.displayedMovies.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val searchText by viewModel.searchText.collectAsState()
    var shouldRefresh = navController.currentBackStackEntry
        ?.savedStateHandle
        ?.get<Boolean>(SHOULD_REFRESH_FAVORITES)
        ?: false

    LaunchedEffect(key1 = true) {
        setAppBarState(
            AppBarState(title = "Favorites", actions = null)
        )
    }
    LaunchedEffect(key1 = shouldRefresh) {
        viewModel.getFavoriteMovies(true)
        shouldRefresh = false
    }

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            Modifier.verticalScroll(rememberScrollState())
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(12.dp)
                        .size(48.dp)
                        .fillMaxHeight()
                        .align(Alignment.CenterHorizontally)
                )
            } else {
                if (movies.isNotEmpty()) {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp, horizontal = 12.dp),
                        value = searchText,
                        onValueChange = { viewModel.onSearchTextChanged(it) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null
                            )
                        },
                    )
                    MoviesGrid(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        movies = movies,
                        loadMore = { },
                        isLoading = false, {
                            navController.navigate(
                                route = "${Screen.Details.route}?movieId=$it",
                                navOptions = NavOptions.Builder().setLaunchSingleTop(true).build()
                            )
                        }
                    )
                } else {
                    Text(
                        text = "You need to add some movies to favorites to see them here",
                        fontSize = 16.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 24.dp, horizontal = 24.dp)
                    )
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        painter = painterResource(id = R.drawable.movie_ill),
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth
                    )
                }
            }
        }
    }
}

const val SHOULD_REFRESH_FAVORITES = "SHOULD_REFRESH_FAVORITES"