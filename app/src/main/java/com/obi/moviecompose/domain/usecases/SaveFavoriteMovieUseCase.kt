package com.obi.moviecompose.domain.usecases

import com.obi.moviecompose.data.models.Movie
import com.obi.moviecompose.domain.MoviesRepository
import com.obi.moviecompose.domain.UseCase

class SaveFavoriteMovieUseCase(
    private val repository: MoviesRepository
) : UseCase<Unit, SaveFavoriteMovieUseCase.Params>() {

    override suspend fun get(params: Params?) {
        requireNotNull(params)
        return repository.saveFavoriteMovie(params.movie)
    }

    data class Params(val movie: Movie)
}