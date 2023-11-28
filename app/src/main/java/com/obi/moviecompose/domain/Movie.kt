package com.obi.moviecompose.domain

import com.obi.moviecompose.data.models.Movie

typealias Movie = DomainMovie

data class DomainMovie(
    val title: String,
    val posterPath: String
)

fun Movie.toDomainMovie() = DomainMovie(this.title, this.posterPath)
