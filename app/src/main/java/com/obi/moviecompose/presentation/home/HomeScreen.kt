package com.obi.moviecompose.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.obi.moviecompose.R
import com.obi.moviecompose.presentation.components.MovieLargeItem
import com.obi.moviecompose.presentation.components.MoviePortraitList
import com.obi.moviecompose.presentation.components.SectionItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = koinViewModel(),
    navController: NavHostController = rememberNavController()
) {
    val trendingMovies by homeViewModel.trendingMovies.collectAsState()
    val topRatedMovies by homeViewModel.topRatedMovies.collectAsState()
    Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier.fillMaxSize()) {
        Column(
            Modifier
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            trendingMovies.let {
                if (it.isNotEmpty()) {
                    SectionItem(icon = R.drawable.ic_trending_24, textRes = R.string.trending_label)
                    MoviePortraitList(
                        movies = it, navController = navController, modifier = Modifier.padding(top = 12.dp)
                    ) {
                        homeViewModel.getTrendingMovies()
                    }
                }
            }
            topRatedMovies.let {
                if (it.isNotEmpty()) {
                    SectionItem(
                        icon = R.drawable.ic_favorite_24,
                        textRes = R.string.popular_label,
                        modifier = Modifier.padding(top = 12.dp)
                    )
                    MoviePortraitList(
                        movies = it,
                        navController = navController,
                        modifier = Modifier.padding(top = 12.dp)
                    ) {
                        homeViewModel.getTopRatedMovies()
                    }
                    MovieLargeItem(
                        posterPath = it.first().posterPath,
                        title = it.first().title,
                        overview = it.first().overview,
                        modifier = Modifier.padding(top = 24.dp, bottom = 24.dp)
                    )
                }
            }

        }
    }
}