package com.obi.moviecompose.presentation.components.common

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.obi.moviecompose.R
import com.obi.moviecompose.data.Consts.BASE_BACKDROP_POSTER_URL
import com.obi.moviecompose.domain.models.Movie

@Composable
fun BackdropPoster(it: Movie, modifier: Modifier = Modifier) {
    AsyncImage(
        model = BASE_BACKDROP_POSTER_URL + it.backdropPath,
        contentDescription = "${it.title} large poster",
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f),
        placeholder = painterResource(id = R.drawable.ic_movie_100_gray)
    )
}


@Preview(showBackground = true)
@Composable
private fun BackdropPoster() {
    BackdropPoster(Movie(1, "Movie Title", "OVERVIEASFASVASVASSA", "", "", ""))
}