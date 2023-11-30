package com.obi.moviecompose.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.obi.moviecompose.domain.Movie

@Composable
fun MoviePortraitList(
    modifier: Modifier = Modifier,
    movies: List<Movie>,
    navController: NavHostController?,
    loadMore: () -> Unit,
) {
    val listState: LazyListState = rememberLazyListState()
    LazyRow(modifier, horizontalArrangement = Arrangement.spacedBy(12.dp), state = listState) {
        items(movies.size) { index ->
            val movie = movies[index]
            MoviePortraitItem(
                movie.posterPath,
                movie.title,
                onItemClicked = { navController?.navigate("movieDetails/${movie.id}") })
        }
    }

    listState.OnHalfElementsReached {
        loadMore()
    }
}

@Preview(showBackground = true)
@Composable
fun MoviePortraitListPreview() {
    MoviePortraitList(
        Modifier.padding(8.dp),
        listOf(
            Movie(
                1,
                "First Title",
                "First Name",
                ""
            ),
            Movie(2, "Second Title", "Second name", ""),
            Movie(3, "Second Title", "Second name", ""),
            Movie(4, "Second Title", "Second name", ""),
        ), loadMore = {}, navController = null
    )
}
