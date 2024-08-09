package com.obi.moviecompose.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.obi.moviecompose.R
import com.obi.moviecompose.presentation.FilterAction
import com.obi.moviecompose.presentation.Screen
import com.obi.moviecompose.presentation.components.MoviesGrid
import com.obi.moviecompose.presentation.home.TabSection.Companion.getTabByPosition
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    navController: NavHostController,
    filterAction: FilterAction
) {
    val movies by viewModel.shownMovies.collectAsState()
    val loadingState by viewModel.loadingState.collectAsState()
    val selectedTab by viewModel.selectedTab.collectAsState()

    val filteredMovies by remember {
        mutableStateOf(
            when (filterAction) {
                FilterAction.NONE -> movies
                FilterAction.BY_RATING_ASCENDING -> movies.sortedBy { it.voteAverage }
                FilterAction.BY_RATING_DESCENDING -> movies.sortedByDescending { it.voteAverage }
                FilterAction.BY_DATE_ASCENDING -> movies.sortedBy { it.releaseDate }
                FilterAction.BY_DATE_DESCENDING -> movies.sortedByDescending { it.releaseDate }
            }
        )
    }

    Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier.fillMaxSize()) {
        Column(
            Modifier.verticalScroll(rememberScrollState())
        ) {
            val tabs = listOf(
                TabSection.NowPlaying,
                TabSection.Popular,
                TabSection.TopRated
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
                    movies = filteredMovies,
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
    data object NowPlaying : TabSection(R.string.now_playing_label, 0)
    data object Popular : TabSection(R.string.popular_label, 1)
    data object TopRated : TabSection(R.string.trending_label, 2)

    companion object {
        fun getTabByPosition(position: Int) = when (position) {
            0 -> NowPlaying
            1 -> Popular
            2 -> TopRated
            else -> NowPlaying
        }
    }
}