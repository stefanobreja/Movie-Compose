package com.obi.moviecompose.data

import com.obi.moviecompose.data.models.Credits
import com.obi.moviecompose.data.models.Movie
import com.obi.moviecompose.data.models.MovieResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesRepository(
    private val movieApi: MovieApi,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun getTopRatedMovies(pageNumber: Int): MovieResult = withContext(ioDispatcher) {
        movieApi.getTopRatedMovies(pageNumber)
    }

    suspend fun getMovieDetails(movieId: Int): Movie = withContext(ioDispatcher) {
        movieApi.getMovieDetails(movieId = movieId)
    }

    suspend fun getTrendingMovies(pageNumber: Int): MovieResult = withContext(ioDispatcher) {
        movieApi.getTrendingMovies(page = pageNumber)
    }

    suspend fun getAiringTodayTvShows(pageNumber: Int): MovieResult = withContext(ioDispatcher) {
        movieApi.getAiringTodayTvShows(pageNumber)
    }

    suspend fun getMovieCredits(movieId: Int): Credits = withContext(ioDispatcher) {
        movieApi.getMovieCredits(movieId = movieId)
    }
}