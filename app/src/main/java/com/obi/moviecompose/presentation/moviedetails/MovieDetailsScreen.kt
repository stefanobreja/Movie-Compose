package com.obi.moviecompose.presentation.moviedetails

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.RememberObserver
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.obi.moviecompose.R
import com.obi.moviecompose.domain.models.Movie
import com.obi.moviecompose.domain.models.Person
import com.obi.moviecompose.presentation.components.PersonItemList
import com.obi.moviecompose.presentation.components.common.BackdropPoster
import com.obi.moviecompose.presentation.components.common.Poster
import org.koin.androidx.compose.koinViewModel

@Composable
fun MovieDetailsScreen(
    movieId: Int?,
    navController: NavController?,
    detailsViewModel: MovieDetailsViewModel = koinViewModel(),
) {
    if (movieId != null) {
        detailsViewModel.getMovieDetails(movieId)
        detailsViewModel.getMovieCredits(movieId)
    }
    val movie by detailsViewModel.movie.collectAsState()
    val casts by detailsViewModel.casts.collectAsState()
    val crews by detailsViewModel.crews.collectAsState()
    Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier.fillMaxSize()) {
        Column {
            ConstraintLayout {
                val (backdrop,
                    poster,
                    title,
                    pager) = createRefs()
                movie?.let {
                    BackdropPoster(it, modifier = Modifier.constrainAs(backdrop) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    })

                    Poster(
                        posterPath = it.posterPath, title = it.title, modifier = Modifier
                            .width(150.dp)
                            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.card_corner_radius)))
                            .constrainAs(poster) {
                                top.linkTo(backdrop.bottom)
                                bottom.linkTo(backdrop.bottom)
                                start.linkTo(parent.start)
                            }
                            .padding(start = 24.dp, top = 56.dp)

                    )

                    Text(
                        text = it.title, fontSize = 24.sp, modifier = Modifier
                            .padding(start = 16.dp, top = 16.dp)
                            .constrainAs(title) {
                                start.linkTo(poster.end)
                                end.linkTo(parent.end)
                                top.linkTo(backdrop.bottom)
                                width = Dimension.fillToConstraints
                            })

                    MovieDetailsPager(
                        movie = it,
                        modifier = Modifier.constrainAs(pager) {
                            top.linkTo(poster.bottom)
                        }, casts = casts, crews = crews
                    )
                } ?: Text(text = "Movie not loaded yet or there is an isue.")
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieDetailsPager(
    modifier: Modifier = Modifier,
    movie: Movie,
    casts: List<Person.Cast>,
    crews: List<Person.Crew>
) {
    val tabItems = listOf("Overview", "Cast & Crew", "Reviews", "Similar Movies")
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val pagerState = rememberPagerState {
        tabItems.size
    }
    LaunchedEffect(selectedTabIndex) {
        pagerState.animateScrollToPage(selectedTabIndex)
    }
    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress) {
            selectedTabIndex = pagerState.currentPage
        }
    }

    Column(modifier) {
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabItems.forEachIndexed { index, tabName ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = {
                        selectedTabIndex = index
                    },
                    text = { Text(text = tabName) }
                )

            }
        }
        HorizontalPager(
            state = pagerState,
            userScrollEnabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) { index ->
            when (index) {
                0 -> {
                    OverviewTab(movie)
                }

                1 -> {
                    CastAndCrewTab(casts, crews)
                }

                2 -> {}
                3 -> {}
            }
        }
    }

}

@Composable
private fun CastAndCrewTab(casts: List<Person.Cast>, crews: List<Person.Crew>) {
    Column(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        PersonItemList(persons = casts)
        PersonItemList(persons = crews)
    }

}

@Composable
private fun OverviewTab(movie: Movie) {
    Column(modifier = Modifier.padding(16.dp)) {
        val releaseDate = movie.releaseDate
        if (releaseDate.isNotBlank()) {
            Text(text = "Release date: $releaseDate")
        }
        Text(
            text = movie.overview,
            modifier = Modifier.padding(top = 12.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMovieDetailsScreenPreview() {
    CastAndCrewTab(
        casts = listOf(
            Person.Cast("", "Test"),
            Person.Cast("", "Test"),
            Person.Cast("", "Test"),
            Person.Cast("", "Test")
        ),
        crews = listOf(
            Person.Crew("", "Test"),
            Person.Crew("", "Test"),
            Person.Crew("", "Test"),
            Person.Crew("", "Test")
        )
    )
}

//@Preview(showBackground = true, apiLevel = 33)
//@Composable
//fun MovieDetailsScreenPreview() {
//    MovieDetailsScreen(1, null, detailsViewModel = koinViewModel())
//}