package com.obi.moviecompose.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.obi.moviecompose.domain.Movie

@Composable
fun MoviesGrid(
    modifier: Modifier = Modifier,
    movies: List<Movie>,
    loadMore: () -> Unit,
    isLoading: Boolean,
    onMovieClicked: (Int) -> Unit,
    listState: LazyGridState = rememberLazyGridState()
) {
    val shouldShowMore by remember { derivedStateOf { listState.reachedBottom() } }
    LaunchedEffect(shouldShowMore) {
        if (shouldShowMore) loadMore()
    }
    LazyVerticalGrid(
        modifier = modifier.padding(8.dp),
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        state = listState
    ) {
        items(movies) { movie ->
            MoviePortraitItem(movie = movie) {
                movie.id?.let { onMovieClicked(it) }
            }
        }
        item(span = { GridItemSpan(maxLineSpan) }) {
            if (isLoading) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                )
            }
        }
    }
}