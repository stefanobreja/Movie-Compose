package com.obi.moviecompose.presentation.details

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.obi.moviecompose.data.Consts
import com.obi.moviecompose.presentation.AppBarState
import com.obi.moviecompose.presentation.favorites.SHOULD_REFRESH_FAVORITES
import org.koin.androidx.compose.koinViewModel
import kotlin.math.roundToInt

@Composable
fun MovieDetailsScreen(
    viewModel: MovieDetailsViewModel = koinViewModel(),
    movieId: Int,
    navController: NavController,
    setAppBarState: (AppBarState) -> Unit
) {
    val movie = viewModel.movie.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()

    BackHandler {
        navController.currentBackStackEntry?.savedStateHandle?.set(SHOULD_REFRESH_FAVORITES, true)
    }

    LaunchedEffect(key1 = true) {
        setAppBarState(
            AppBarState(title = movie.value.title, actions = null)
        )
    }

    LaunchedEffect(true) {
        if (movieId > 0)
            viewModel.getMovieDetails(movieId)
    }

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            if (isLoading.value) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(12.dp)
                        .size(48.dp)
                        .fillMaxHeight()
                        .align(Alignment.CenterHorizontally)
                )

            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        model = Consts.BASE_POSTER_POSTER_URL + movie.value.backDropPath,
                        contentDescription = null
                    )
                    Text(
                        text = movie.value.title,
                        fontSize = 28.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .background(Color.Black.copy(alpha = 0.3f))
                            .padding(4.dp)
                            .align(Alignment.BottomStart)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                ) {
                    movie.value.genres?.mapNotNull { it.name }?.joinToString("/")?.let {
                        Text(
                            text = it,
                            fontSize = 14.sp,
                            modifier = Modifier
                                .wrapContentHeight()
                                .weight(1f)
                                .padding(top = 24.dp, start = 24.dp, end = 8.dp)
                        )
                    }
                    val isFavorite = movie.value.isFavorite
                    val favoriteIconTint = if (isFavorite) Color.Red
                    else MaterialTheme.colorScheme.onSurface

                    IconButton(onClick = {
                        viewModel.onFavoriteClicked(isFavorite)
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = "Favorite button",
                            tint = favoriteIconTint
                        )
                    }
                }
                movie.value.releaseDate?.let {
                    Text(
                        text = it,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(top = 4.dp, start = 24.dp, end = 24.dp)
                    )
                }
                Row(modifier = Modifier.padding(top = 4.dp, start = 24.dp, end = 24.dp)) {
                    val averageVote = (movie.value.voteAverage?.roundToInt()?.div(2)) ?: 0
                    for (vote in 1..5) {
                        val voteColor = if (vote <= averageVote) {
                            MaterialTheme.colorScheme.surfaceTint
                        } else {
                            MaterialTheme.colorScheme.onSurface
                        }
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = null,
                            tint = voteColor
                        )
                    }
                    Text(
                        text = "(${movie.value.voteCount.toString()} votes)",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }

                Text(
                    text = "Overview",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(top = 8.dp, start = 24.dp, end = 24.dp)
                )
                Text(
                    text = movie.value.overview,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(top = 8.dp, start = 24.dp, end = 24.dp)
                )
            }
        }
    }
}