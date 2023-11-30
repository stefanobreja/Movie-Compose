package com.obi.moviecompose.domain

import com.obi.moviecompose.data.models.Movie

typealias Movie = DomainMovie

data class DomainMovie(
    val id: Int,
    val title: String,
    val posterPath: String,
    val overview: String
)

fun Movie.toDomainMovie() = DomainMovie(this.id, this.title, this.posterPath, this.overview)
