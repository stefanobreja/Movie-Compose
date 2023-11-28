package com.obi.moviecompose.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.obi.moviecompose.R

@Composable
fun MoviePortraitItem(movieUrl: String?, movieName: String, modifier: Modifier = Modifier) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = modifier,
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.card_corner_radius))
    ) {
        Column {
            AsyncImage(
                model = movieUrl,
                contentDescription = null,
                placeholder = painterResource(id = R.drawable.ic_movie_100_gray),
                modifier = Modifier
                    .width(150.dp)
                    .aspectRatio(3f / 4f, true),
                contentScale = ContentScale.FillWidth
            )
            Text(
                text = movieName,
                modifier = Modifier
                    .padding(start = 8.dp),
                fontWeight = FontWeight.Medium,
                fontSize = 24.sp
            )
        }
    }
}

@Preview
@Composable
fun MoviePortraitItemPreview() {
    MoviePortraitItem(null, "Movie name", Modifier.padding(8.dp))
}