package com.obi.moviecompose.domain.usecases

import com.obi.moviecompose.data.models.Movie
import com.obi.moviecompose.domain.DomainMovie
import com.obi.moviecompose.domain.MoviesRepository
import com.obi.moviecompose.domain.UseCase
import com.obi.moviecompose.domain.toDomainMovie

class GetTopRatedMoviesUseCase(
    private val repository: MoviesRepository
) : UseCase<GetTopRatedMoviesUseCase.Response, GetTopRatedMoviesUseCase.Params>() {

    override suspend fun get(params: Params?): Response {
        requireNotNull(params)
        val result = repository.getTopRatedMovies(params.pageNumber)
        return result.movies?.map(Movie::toDomainMovie).let {
            Response(it.orEmpty(), result.totalPages)
        }
    }

    data class Response(val movies: List<DomainMovie>, val numberOfPages: Int?)
    data class Params(val pageNumber: Int)

}