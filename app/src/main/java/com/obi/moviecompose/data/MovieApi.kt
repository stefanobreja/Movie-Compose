package com.obi.moviecompose.data

import com.obi.moviecompose.data.models.Movie
import com.obi.moviecompose.data.models.MovieResult
import com.obi.moviecompose.data.utils.TimeWindow
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int
    ): MovieResult

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movieId: Int): Movie

    @GET("trending/movie/{time_window}")
    suspend fun getTrendingMovies(
        @Path("time_window") timeWindow: String = TimeWindow.DAY.name.lowercase(),
        @Query("page") page: Int
    ): MovieResult

    @GET("tv/airing_today")
    suspend fun getAiringTodayTvShows(
        @Query("page") page: Int
    ): MovieResult
}