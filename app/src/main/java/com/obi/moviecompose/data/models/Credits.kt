package com.obi.moviecompose.data.models

data class Credits(
    val cast: List<Cast>,
    val crew: List<Crew>,
    val id: Int
)