package com.obi.moviecompose.domain

import com.obi.moviecompose.data.MoviesRepository

class GetTopRatedMoviesUseCase(
    private val repository: MoviesRepository
) : UseCase<GetTopRatedMoviesUseCase.Response, Unit>() {

    override suspend fun get(params: Unit?): Response {
        val result = repository.getTopRatedMovies()
        return Response(result.movies.map(com.obi.moviecompose.data.models.Movie::toDomainMovie), result.totalPages)
    }

    data class Response(val movies: List<Movie>, val numberOfPages: Int)
}