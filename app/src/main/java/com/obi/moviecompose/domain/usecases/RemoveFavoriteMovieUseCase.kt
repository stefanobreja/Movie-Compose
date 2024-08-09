package com.obi.moviecompose.domain.usecases

import com.obi.moviecompose.data.models.Movie
import com.obi.moviecompose.domain.MoviesRepository
import com.obi.moviecompose.domain.UseCase

class RemoveFavoriteMovieUseCase(
    private val repository: MoviesRepository
) : UseCase<Unit, RemoveFavoriteMovieUseCase.Params>() {

    override suspend fun get(params: Params?) {
        requireNotNull(params)
        repository.removeFavoriteMovie(params.movie)
    }

    data class Params(val movie: Movie)
}