package com.obi.moviecompose.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.obi.moviecompose.data.models.Movie

@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: Movie)

    @Delete
    fun removeMovie(movie: Movie)

    @Query("SELECT * from movies")
    fun getMovies(): List<Movie>
}