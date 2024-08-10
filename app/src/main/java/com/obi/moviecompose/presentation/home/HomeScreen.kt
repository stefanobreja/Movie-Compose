package com.obi.moviecompose.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.obi.moviecompose.R
import com.obi.moviecompose.presentation.AppBarState
import com.obi.moviecompose.presentation.Screen
import com.obi.moviecompose.presentation.components.MoviesGrid
import com.obi.moviecompose.presentation.home.TabSection.Companion.getTabByPosition
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    navController: NavHostController,
    setAppBarState: (AppBarState) -> Unit
) {
    val movies by viewModel.shownMovies.collectAsState()
    val loadingState by viewModel.loadingState.collectAsState()
    val selectedTab by viewModel.selectedTab.collectAsState()

    var showMenu by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        setAppBarState(
            AppBarState(title = "Home",
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
                                viewModel.onFilterByRatingAscending()
                            })
                        DropdownMenuItem(
                            text = { Text(text = "Filter by rating descending") },
                            onClick = {
                                showMenu = false
                                viewModel.onFilterByRatingDescending()

                            })
                        DropdownMenuItem(
                            text = { Text(text = "Filter by date ascending") },
                            onClick = {
                                showMenu = false
                                viewModel.onFilterByDateAscending()
                            })
                        DropdownMenuItem(
                            text = { Text(text = "Filter by date descending") },
                            onClick = {
                                showMenu = false
                                viewModel.onFilterByDateDescending()
                            })
                    }
                })
        )

    }

    Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier.fillMaxSize()) {
        Column(
            Modifier.verticalScroll(rememberScrollState())
        ) {
            val tabs = listOf(
                TabSection.Popular,
                TabSection.TopRated,
                TabSection.NowPlaying
            )

            TabRow(
                modifier = Modifier.fillMaxWidth(),
                selectedTabIndex = selectedTab.position
            ) {
                tabs.forEachIndexed { index, tab ->
                    Tab(
                        selected = index == selectedTab.position,
                        onClick = {
                            viewModel.onTabSelected(getTabByPosition(index))
                        },
                        text = { Text(text = stringResource(id = tab.titleRes)) }
                    )
                }
            }

            if (loadingState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(12.dp)
                        .size(48.dp)
                        .fillMaxHeight()
                        .align(Alignment.CenterHorizontally)
                )
            } else {
                MoviesGrid(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    movies = movies,
                    loadMore = { viewModel.loadMore() },
                    isLoading = loadingState.isLoadingMore,
                    onMovieClicked = {
                        navController.navigate("${Screen.Details.route}?movieId=$it")
                    }
                )
            }
        }

    }
}

sealed class TabSection(val titleRes: Int, val position: Int) {
    data object Popular : TabSection(R.string.popular_label, 0)
    data object TopRated : TabSection(R.string.trending_label, 1)
    data object NowPlaying : TabSection(R.string.now_playing_label, 2)

    companion object {
        fun getTabByPosition(position: Int) = when (position) {
            0 -> Popular
            1 -> TopRated
            2 -> NowPlaying
            else -> NowPlaying
        }
    }
}