package com.obi.moviecompose.domain.usecases

import com.obi.moviecompose.domain.DomainMovie
import com.obi.moviecompose.domain.MoviesRepository
import com.obi.moviecompose.domain.UseCase
import com.obi.moviecompose.domain.toDomainMovie

class GetMovieDetailsUseCase(
    private val repository: MoviesRepository
) : UseCase<GetMovieDetailsUseCase.Response, GetMovieDetailsUseCase.Params>() {

    override suspend fun get(params: Params?): Response {
        requireNotNull(params)
        val result = repository.getMovieDetails(params.movieId)
        return Response(result.toDomainMovie())
    }

    data class Response(val movie: DomainMovie?)
    data class Params(val movieId: Int)
}