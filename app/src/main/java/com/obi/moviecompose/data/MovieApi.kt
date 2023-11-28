package com.obi.moviecompose.data

import com.obi.moviecompose.data.models.Movie
import com.obi.moviecompose.data.models.MovieResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(): MovieResult

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Query("api_key") apiKey: String, @Path("movie_id") movieId: Int): Movie
}