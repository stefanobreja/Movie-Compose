package com.obi.moviecompose.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.obi.moviecompose.data.models.Genre
import com.obi.moviecompose.data.models.Movie

@Database(entities = [Movie::class], version = 1)
@TypeConverters(Converters::class)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
}

class Converters {
    @TypeConverter
    fun fromGenreList(genres: List<Genre>): String {
        return Gson().toJson(genres)
    }

    @TypeConverter
    fun toGenreList(genres: String): List<Genre> {
        return Gson().fromJson(genres, Array<Genre>::class.java).toList()
    }

}