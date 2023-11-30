package com.obi.moviecompose.data

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

    suspend fun getTrendingMovies(pageNumber: Int): MovieResult = withContext(ioDispatcher) {
        movieApi.getTrendingMovies(page = pageNumber)
    }

    suspend fun getAiringTodayTvShows(pageNumber: Int): MovieResult = withContext(ioDispatcher) {
        movieApi.getAiringTodayTvShows(pageNumber)
    }
}