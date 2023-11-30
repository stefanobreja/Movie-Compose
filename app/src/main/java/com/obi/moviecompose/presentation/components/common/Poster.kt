package com.obi.moviecompose.presentation.components.common

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.obi.moviecompose.R
import com.obi.moviecompose.data.Consts

@Composable
fun Poster(posterPath: String?, title: String, modifier: Modifier = Modifier) {
    AsyncImage(
        model = Consts.BASE_POSTER_THUMBNAIL_URL + posterPath,
        contentDescription = "$title movie poster",
        placeholder = painterResource(id = R.drawable.ic_movie_100_gray),
        contentScale = ContentScale.FillWidth,
        modifier = modifier.aspectRatio(3f / 4f, true)
    )
}
