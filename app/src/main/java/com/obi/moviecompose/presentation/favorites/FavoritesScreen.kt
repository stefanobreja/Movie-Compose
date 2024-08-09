package com.obi.moviecompose.presentation.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.obi.moviecompose.presentation.Screen
import com.obi.moviecompose.presentation.components.MoviesGrid
import com.obi.moviecompose.presentation.favorites.FavoritesViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun FavoritesScreen(viewModel: FavoritesViewModel = koinViewModel(), navController: NavController) {
    val movies by viewModel.displayedMovies.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val searchText by viewModel.searchText.collectAsState()
    val state = rememberPullRefreshState(isLoading, { viewModel.getFavoriteMovies() })
    Surface(
        color = MaterialTheme.colorScheme.background, modifier = Modifier
            .fillMaxSize()

    ) {
        Column(
            Modifier
                .verticalScroll(rememberScrollState())
                .pullRefresh(state)
        ) {
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
                    navController.navigate("${Screen.Details.route}?movieId=$it")
                    navController.navigate("${Screen.Details.route}?movieId=$it")
                }
            )
        }
    }
}