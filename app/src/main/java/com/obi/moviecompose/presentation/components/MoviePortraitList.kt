package com.obi.moviecompose.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MoviePortraitList(movies: List<Movie>, modifier: Modifier = Modifier) {
    LazyRow(modifier, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        items(movies.size) { index ->
            val movie = movies[index]
            MoviePortraitItem(movie.imageUrl, movie.name)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MoviePortraitListPreview() {
    MoviePortraitList(
        listOf(
            Movie(
                null,
                "First Name"
            ),
            Movie(null, "Second name"),
            Movie(null, "Second name"),
            Movie(null, "Second name"),
            Movie(null, "Second name"),
            Movie(null, "Second name")
        ), Modifier.padding(8.dp)
    )
}

data class Movie(val imageUrl: String?, val name: String)