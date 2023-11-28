package com.obi.moviecompose.data

import com.obi.moviecompose.BuildConfig
import com.obi.moviecompose.data.models.MovieResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.java.KoinJavaComponent.inject

class MoviesRepository(
    private val movieApi: MovieApi,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun getTopRatedMovies(): MovieResult = withContext(ioDispatcher) {
        movieApi.getTopRatedMovies()
    }
}