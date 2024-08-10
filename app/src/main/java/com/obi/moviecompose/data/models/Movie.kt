package com.obi.moviecompose.data.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity("movies")
data class Movie(
    @PrimaryKey(autoGenerate = false)
    var id: Int? = 0,
    var adult: Boolean? = false,
    var backdrop_path: String? = "",
    var budget: Int? = 0,
    var genres: List<Genre>? = emptyList(),
    var homepage: String? = "",
    var original_language: String? = "",
    var overview: String? = "",
    var popularity: Double? = 0.0,
    var poster_path: String? = "",
    var release_date: String? = "",
    var revenue: Int? = 0,
    var runtime: Int? = 0,
    var status: String? = "",
    var tagline: String? = "",
    var title: String? = "",
    var video: Boolean? = false,
    var vote_average: Double? = 0.0,
    var vote_count: Int? = 0,
    var isFavorite: Boolean = false
)