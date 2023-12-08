package com.obi.moviecompose.domain

import com.obi.moviecompose.data.MoviesRepository
import com.obi.moviecompose.data.models.Cast
import com.obi.moviecompose.data.models.Crew
import com.obi.moviecompose.domain.models.Person
import com.obi.moviecompose.domain.models.toPerson

class GetMovieCreditsUseCase(
    private val repository: MoviesRepository
) : UseCase<GetMovieCreditsUseCase.Response, GetMovieCreditsUseCase.Params>() {

    override suspend fun get(params: Params?): Response {
        requireNotNull(params)
        val result = repository.getMovieCredits(params.movieId)
        return Response(
            result.cast.map(Cast::toPerson) + result.crew.map(Crew::toPerson)
        )
    }

    data class Response(val persons: List<Person>)
    data class Params(val movieId: Int)

}