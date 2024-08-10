package com.obi.moviecompose.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.obi.moviecompose.domain.Movie
import com.obi.moviecompose.domain.toDataMovie
import com.obi.moviecompose.domain.usecases.GetMovieDetailsUseCase
import com.obi.moviecompose.domain.usecases.RemoveFavoriteMovieUseCase
import com.obi.moviecompose.domain.usecases.SaveFavoriteMovieUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import logcat.logcat

class MovieDetailsViewModel(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val saveFavoriteMovieUseCase: SaveFavoriteMovieUseCase,
    private val removeFavoriteMovieUseCase: RemoveFavoriteMovieUseCase
) : ViewModel() {
    private val _movie: MutableStateFlow<Movie> = MutableStateFlow(
        Movie(
            1, "", "", "", "",
            emptyList(), "", 1.2, 121, false
        )
    )
    val movie: MutableStateFlow<Movie> = _movie

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isLoading: MutableStateFlow<Boolean> = _isLoading

    fun getMovieDetails(movieId: Int) {
        viewModelScope.launch {
            getMovieDetailsUseCase(GetMovieDetailsUseCase.Params(movieId))
                .onSuccess { response ->
                    delay(1000)
                    _isLoading.value = false
                    response.movie?.let {
                        _movie.value = it
                    }
                }
                .onFailure { error ->
                    logcat {
                        error.message.toString()
                    }
                }
        }
    }

    private fun saveFavoriteMovie(movie: Movie) {
        _isLoading.value = true
        viewModelScope.launch {
            saveFavoriteMovieUseCase(SaveFavoriteMovieUseCase.Params(movie.toDataMovie()))
                .onSuccess {
                    _isLoading.value = false
                }
                .onFailure { error ->
                    _isLoading.value = false
                    logcat { error.message.toString() }
                }
        }
    }

    private fun removeFavoriteMovie(movie: Movie) {
        _isLoading.value = true
        viewModelScope.launch {
            removeFavoriteMovieUseCase(RemoveFavoriteMovieUseCase.Params(movie.toDataMovie()))
                .onSuccess {
                    _isLoading.value = false
                }
                .onFailure { error ->
                    _isLoading.value = false
                    logcat { error.message.toString() }
                }
        }
    }

    fun onFavoriteClicked(favorite: Boolean) {
        _movie.update {
            it.copy(isFavorite = !favorite)
        }
        if (!favorite) {
            saveFavoriteMovie(_movie.value)
        } else {
            removeFavoriteMovie(_movie.value)
        }
    }
}