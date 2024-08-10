package com.obi.moviecompose.data.models

import androidx.room.Entity

@Entity(tableName = "genres")
data class Genre(
    val id: Int?,
    val name: String?
)