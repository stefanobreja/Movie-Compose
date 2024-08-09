package com.obi.moviecompose.domain

import com.obi.moviecompose.data.models.Genre
import com.obi.moviecompose.data.models.Movie

typealias Movie = DomainMovie

data class DomainMovie(
    val id: Int?,
    val title: String,
    val posterPath: String,
    val backDropPath: String,
    val overview: String,
    val genres: List<Genre>?,
    val releaseDate: String?,
    val voteAverage: Double?,
    val voteCount: Int?,
    val isFavorite: Boolean
)

fun Movie.toDomainMovie() =
    DomainMovie(
        this.id,
        this.title.orEmpty(),
        this.poster_path.orEmpty(),
        this.backdrop_path.orEmpty(),
        this.overview.orEmpty(),
        this.genres,
        this.release_date,
        this.vote_average,
        this.vote_count,
        this.isFavorite
    )

fun DomainMovie.toDataMovie() = Movie(
    id = this.id,
    title =  this.title,
    poster_path =  this.posterPath,
    backdrop_path = this.backDropPath,
    overview = this.overview,
    genres =  this.genres,
    release_date =  this.releaseDate,
    vote_average =  this.voteAverage,
    vote_count =  this.voteCount,
    isFavorite = this.isFavorite
)
