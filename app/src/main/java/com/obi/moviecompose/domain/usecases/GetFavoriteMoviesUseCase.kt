package com.obi.moviecompose.domain.usecases

import com.obi.moviecompose.data.models.Movie
import com.obi.moviecompose.domain.DomainMovie
import com.obi.moviecompose.domain.MoviesRepository
import com.obi.moviecompose.domain.UseCase
import com.obi.moviecompose.domain.toDomainMovie

class GetFavoriteMoviesUseCase(
    private val repository: MoviesRepository
) : UseCase<GetFavoriteMoviesUseCase.Response, Unit>() {

    override suspend fun get(params: Unit?): Response {
        val result = repository.getFavoriteMovies()
        return Response(result.map(Movie::toDomainMovie))
    }

    data class Response(val movies: List<DomainMovie>?)
}