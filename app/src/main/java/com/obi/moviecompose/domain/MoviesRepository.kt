package com.obi.moviecompose.domain

import com.obi.moviecompose.data.local.MoviesDao
import com.obi.moviecompose.data.models.Movie
import com.obi.moviecompose.data.models.MovieResult
import com.obi.moviecompose.data.network.MovieApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesRepository(
    private val movieApi: MovieApi,
    private val moviesDao: MoviesDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) {
    suspend fun getTopRatedMovies(pageNumber: Int): MovieResult = withContext(ioDispatcher) {
        movieApi.getTopRatedMovies(page = pageNumber)
    }

    suspend fun getTrendingMovies(pageNumber: Int): MovieResult = withContext(ioDispatcher) {
        movieApi.getTrendingMovies(page = pageNumber)
    }

    suspend fun getAiringTodayTvShows(pageNumber: Int): MovieResult = withContext(ioDispatcher) {
        movieApi.getAiringTodayTvShows(page = pageNumber)
    }

    suspend fun searchMovie(movieTitle: String): MovieResult = withContext(ioDispatcher) {
        movieApi.searchMovie(query = movieTitle)
    }

    suspend fun getMovieDetails(movieId: Int): Movie = withContext(ioDispatcher) {
        val daoMovie = moviesDao.getMovies().find { it.id == movieId }
        daoMovie ?: movieApi.getMovieDetails(movieId = movieId)
    }

    suspend fun saveFavoriteMovie(movie: Movie) = withContext(ioDispatcher) {
        moviesDao.insertMovie(movie.copy(isFavorite = true))
    }

    suspend fun removeFavoriteMovie(movie: Movie) = withContext(ioDispatcher) {
        moviesDao.removeMovie(movie)
    }

    suspend fun getFavoriteMovies(): List<Movie> = withContext(ioDispatcher) {
        moviesDao.getMovies()
    }
}