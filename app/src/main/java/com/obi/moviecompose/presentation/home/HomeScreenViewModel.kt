package com.obi.moviecompose.presentation.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.obi.moviecompose.domain.GetTopRatedMoviesUseCase
import com.obi.moviecompose.domain.Movie
import com.obi.moviecompose.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class HomeScreenViewModel(getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase) : BaseViewModel() {

    private val _events: MutableState<Event?> = mutableStateOf(null)
    private val events: State<Event?> = _events

    private val currentPage = 0

    private val _movies: MutableState<List<Movie>> = mutableStateOf(listOf())
    val movies: State<List<Movie>> = _movies

    init {
        viewModelScope.launch {
            getTopRatedMoviesUseCase()
                .onSuccess { response ->
                    _movies.value = response.movies
                }
                .onFailure { error ->
                    error.message?.let {
                        _events.value = Event.ShowError(it)
                    }
                }
        }
    }

    sealed class Event {
        data class ShowError(val errorMessage: String) : Event()
    }
}