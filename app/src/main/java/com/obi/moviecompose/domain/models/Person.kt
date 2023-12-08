package com.obi.moviecompose.domain.models

import com.obi.moviecompose.data.models.Cast
import com.obi.moviecompose.data.models.Crew

sealed class Person(
    open val profilePath: String,
    open val name: String
) {
    data class Cast(override val profilePath: String, override val name: String) : Person(profilePath, name)
    data class Crew(override val profilePath: String, override val name: String) : Person(profilePath, name)
}

fun Cast.toPerson() = Person.Cast(this.profilePath, this.name)
fun Crew.toPerson() = Person.Crew(this.profilePath, this.name)