package com.obi.moviecompose.presentation.moviedetails

import androidx.lifecycle.viewModelScope
import com.obi.moviecompose.data.models.Cast
import com.obi.moviecompose.domain.GetMovieCreditsUseCase
import com.obi.moviecompose.domain.GetMovieDetailsUseCase
import com.obi.moviecompose.domain.models.Movie
import com.obi.moviecompose.domain.models.Person
import com.obi.moviecompose.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getMovieCredits: GetMovieCreditsUseCase
) : BaseViewModel() {
    private val _movie: MutableStateFlow<Movie?> = MutableStateFlow(null)
    val movie: StateFlow<Movie?> = _movie

    private val _casts: MutableStateFlow<List<Person.Cast>> = MutableStateFlow(emptyList())
    val casts: StateFlow<List<Person.Cast>> = _casts

    private val _crews: MutableStateFlow<List<Person.Crew>> = MutableStateFlow(emptyList())
    val crews: StateFlow<List<Person.Crew>> = _crews


    fun getMovieDetails(movieId: Int) {
        viewModelScope.launch {
            getMovieDetailsUseCase(GetMovieDetailsUseCase.Params(movieId))
                .onSuccess { response ->
                    _movie.emit(response.movie)
                }
                .onFailure {
                    emitEvent(Event.ShowError("Something went wrong"))
                }
        }
    }

    fun getMovieCredits(movieId: Int) {
        viewModelScope.launch {
            getMovieCredits(GetMovieCreditsUseCase.Params(movieId))
                .onSuccess { result ->
                    val casts = result.persons.filterIsInstance<Person.Cast>()
                    val crews = result.persons.filterIsInstance<Person.Crew>()
                    _casts.value = casts
                    _crews.value = crews
                }
                .onFailure {
                    emitEvent(Event.ShowError("Something went wrong"))
                }

        }
    }
}