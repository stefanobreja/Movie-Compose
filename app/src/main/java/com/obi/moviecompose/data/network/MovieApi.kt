package com.obi.moviecompose.data.network

import com.obi.moviecompose.BuildConfig
import com.obi.moviecompose.data.models.Movie
import com.obi.moviecompose.data.models.MovieResult
import com.obi.moviecompose.data.utils.TimeWindow
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey: String = BuildConfig.apiKey,
        @Query("page") page: Int
    ): MovieResult

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.apiKey
    ): Movie

    @GET("trending/movie/{time_window}")
    suspend fun getTrendingMovies(
        @Path("time_window") timeWindow: String = TimeWindow.DAY.name.lowercase(),
        @Query("api_key") apiKey: String = BuildConfig.apiKey,
        @Query("page") page: Int
    ): MovieResult

    @GET("tv/airing_today")
    suspend fun getAiringTodayTvShows(
        @Query("api_key") apiKey: String = BuildConfig.apiKey,
        @Query("page") page: Int
    ): MovieResult

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("api_key") apiKey: String = BuildConfig.apiKey,
        @Query("query") query: String,
    ):MovieResult
}