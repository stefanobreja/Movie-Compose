package com.obi.moviecompose.presentation.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.obi.moviecompose.data.MoviesRepository
import com.obi.moviecompose.data.models.Movie
import com.obi.moviecompose.domain.GetTopRatedMoviesUseCase
import com.obi.moviecompose.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class HomeScreenViewModel(getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase) : BaseViewModel() {

    val events: MutableState<Event?> = mutableStateOf(null)

    private val currentPage = 0

    val movies: MutableState<List<Movie>> = mutableStateOf(listOf())

    init {
        viewModelScope.launch {
            getTopRatedMoviesUseCase()
                .onSuccess {
                    it
                }
                .onFailure {
                    it
                }
        }
    }

    sealed class Event {
        data class ShowError(val errorMessage: String) : Event()
    }
}