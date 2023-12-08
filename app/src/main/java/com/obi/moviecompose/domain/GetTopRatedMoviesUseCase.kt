package com.obi.moviecompose.domain

import com.obi.moviecompose.data.MoviesRepository
import com.obi.moviecompose.data.models.Movie
import com.obi.moviecompose.domain.models.DomainMovie
import com.obi.moviecompose.domain.models.toDomainMovie

class GetTopRatedMoviesUseCase(
    private val repository: MoviesRepository
) : UseCase<GetTopRatedMoviesUseCase.Response, GetTopRatedMoviesUseCase.Params>() {

    override suspend fun get(params: Params?): Response {
        requireNotNull(params)
        val result = repository.getTopRatedMovies(params.pageNumber)
        return Response(result.movies.map(Movie::toDomainMovie), result.totalPages)
    }

    data class Response(val movies: List<DomainMovie>, val numberOfPages: Int)
    data class Params(val pageNumber: Int)

}