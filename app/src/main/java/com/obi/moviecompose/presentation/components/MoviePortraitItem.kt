package com.obi.moviecompose.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.obi.moviecompose.R
import com.obi.moviecompose.presentation.components.common.Poster

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviePortraitItem(movieUrl: String?, movieName: String, modifier: Modifier = Modifier, onItemClicked: () -> Unit) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = modifier
            .width(150.dp),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.card_corner_radius)),
        onClick = onItemClicked
    ) {
        Column(Modifier.fillMaxWidth()) {
            Poster(posterPath = movieUrl, title = movieName)
            Text(
                text = movieName,
                modifier = Modifier
                    .padding(8.dp),
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 16.sp
            )
        }
    }
}

@Preview
@Composable
fun MoviePortraitItemPreview() {
    MoviePortraitItem(null, "Movie name veryyyy loong", Modifier.padding(8.dp), onItemClicked = {})
}