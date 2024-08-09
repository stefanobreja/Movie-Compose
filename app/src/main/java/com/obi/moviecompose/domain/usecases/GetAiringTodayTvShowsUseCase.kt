package com.obi.moviecompose.domain.usecases

import com.obi.moviecompose.data.models.Movie
import com.obi.moviecompose.domain.DomainMovie
import com.obi.moviecompose.domain.MoviesRepository
import com.obi.moviecompose.domain.UseCase
import com.obi.moviecompose.domain.toDomainMovie

class GetAiringTodayTvShowsUseCase(
    private val repository: MoviesRepository
) : UseCase<GetAiringTodayTvShowsUseCase.Response, GetAiringTodayTvShowsUseCase.Params>() {

    override suspend fun get(params: Params?): Response {
        requireNotNull(params)
        val result = repository.getAiringTodayTvShows(params.pageNumber)
        return result.movies?.map(Movie::toDomainMovie).let {
            Response(it.orEmpty(), result.totalPages)
        }
    }

    data class Response(val movies: List<DomainMovie>, val numberOfPages: Int?)
    data class Params(val pageNumber: Int)

}