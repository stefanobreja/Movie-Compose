package com.obi.moviecompose.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.obi.moviecompose.R
import com.obi.moviecompose.domain.Movie
import com.obi.moviecompose.presentation.components.common.Poster
import java.math.RoundingMode
import kotlin.math.roundToInt

@Composable
fun MoviePortraitItem(
    movie: Movie,
    modifier: Modifier = Modifier,
    onItemClicked: () -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = modifier
            .width(150.dp),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.card_corner_radius)),
        onClick = onItemClicked
    ) {
        Column(Modifier.fillMaxWidth()) {
            Poster(posterPath = movie.posterPath, title = movie.title)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = movie.releaseDate?.take(4).toString(), fontSize = 16.sp)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        Modifier.size(16.dp)
                    )
                    Text(
                        text = movie.voteAverage?.toBigDecimal()
                            ?.setScale(1, RoundingMode.HALF_EVEN).toString(),
                        fontSize = 16.sp
                    )
                }
                val isFavorite = movie.isFavorite
                val icon = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun MoviePortraitItemPreview() {
    MoviePortraitItem(
        Movie(
            1,
            "Movie name very very very very long",
            "",
            "",
            "",
            emptyList(),
            "2024-06-20",
            7.34412,
            0,
            false
        ), Modifier.padding(8.dp), onItemClicked = {})
}