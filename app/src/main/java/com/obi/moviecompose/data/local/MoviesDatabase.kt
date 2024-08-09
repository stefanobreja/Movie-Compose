package com.obi.moviecompose.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.obi.moviecompose.data.models.Movie

@Database(entities = [Movie::class], version = 1)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
}