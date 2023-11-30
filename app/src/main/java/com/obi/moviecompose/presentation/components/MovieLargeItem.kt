package com.obi.moviecompose.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.obi.moviecompose.R
import com.obi.moviecompose.presentation.components.common.Poster

@Composable
fun MovieLargeItem(posterPath: String, title: String, overview: String, modifier: Modifier = Modifier) {

    Card(
        modifier = modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.card_corner_radius)),
    ) {
        Row {
            Poster(posterPath = posterPath, title = title, modifier = Modifier.width(150.dp))
            Column(modifier = Modifier.padding(8.dp)) {
                Text(text = title, fontSize = 24.sp)
                Text(
                    text = overview,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 5,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun MovieLargeItemPreview() {
    MovieLargeItem(
        "/path",
        "Title",
        "Very long movie description with a a lot of details about the movie that will never be displayed in full ever ever evereverevereverevereverevereverevereverevereverevereverevereverevereverevereverever ",
        Modifier.padding(24.dp)
    )
}