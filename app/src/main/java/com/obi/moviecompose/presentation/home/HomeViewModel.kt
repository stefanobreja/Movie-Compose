package com.obi.moviecompose.presentation.home

import androidx.lifecycle.viewModelScope
import com.obi.moviecompose.domain.GetAiringTodayTvShowsUseCase
import com.obi.moviecompose.domain.GetTopRatedMoviesUseCase
import com.obi.moviecompose.domain.GetTrendingMoviesUseCase
import com.obi.moviecompose.domain.models.Movie
import com.obi.moviecompose.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getAiringTodayTvShowsUseCase: GetAiringTodayTvShowsUseCase
) : BaseViewModel() {

    private var trendingCurrentPage = 1
    private val _trendingMovies: MutableStateFlow<MutableList<Movie>> = MutableStateFlow(mutableListOf())
    val trendingMovies: StateFlow<List<Movie>> = _trendingMovies

    private var topRatedCurrentPage = 1
    private val _topRatedMovies: MutableStateFlow<List<Movie>> = MutableStateFlow(mutableListOf())
    val topRatedMovies: StateFlow<List<Movie>> = _topRatedMovies

    init {
        viewModelScope.launch {
            getTrendingMovies()
            getTopRatedMovies()
        }
    }

    fun getTrendingMovies() {
        viewModelScope.launch {
            getTrendingMoviesUseCase(GetTrendingMoviesUseCase.Params(trendingCurrentPage))
                .onSuccess { response ->
                    _trendingMovies.value += response.movies
                    _trendingMovies.emit(_trendingMovies.value)
                    trendingCurrentPage += 1
                }
                .onFailure { error ->
                    error.message?.let {
                        emitEvent(Event.ShowError(it))
                    }
                }
        }
    }

    fun getTopRatedMovies() {
        viewModelScope.launch {
            getTopRatedMoviesUseCase(GetTopRatedMoviesUseCase.Params(topRatedCurrentPage))
                .onSuccess { response ->
                    _topRatedMovies.value += response.movies
                    topRatedCurrentPage += 1
                }
                .onFailure { error ->
                    error.message?.let {
                        emitEvent(Event.ShowError(it))
                    }
                }
        }
    }

}