package com.obi.moviecompose.presentation.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.obi.moviecompose.R

@Composable
fun PopularArtistsItem(movieUrl: String?, movieName: String, modifier: Modifier = Modifier) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier.width(80.dp)) {
        AsyncImage(
            model = movieUrl,
            contentDescription = null,
            placeholder = painterResource(id = R.drawable.ic_person_100_gray),
            modifier = Modifier
                .width(80.dp)
                .aspectRatio(3f / 4f, true)
                .clip(RoundedCornerShape(80.dp)),
            contentScale = ContentScale.FillWidth,


            )
        Text(
            text = movieName,
            fontWeight = FontWeight.Thin,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PopularArtistsItemPreview() {
    PopularArtistsItem(null, "Movie name looong", Modifier.padding(0.dp))
}