package com.obi.moviecompose.domain.models

import com.obi.moviecompose.data.models.Movie

typealias Movie = DomainMovie

data class DomainMovie(
    val id: Int,
    val title: String,
    val posterPath: String,
    val overview: String,
    val backdropPath: String,
    val releaseDate: String
)

fun Movie.toDomainMovie() =
    DomainMovie(this.id, this.title, this.posterPath, this.overview, this.backdropPath, this.releaseDate)
