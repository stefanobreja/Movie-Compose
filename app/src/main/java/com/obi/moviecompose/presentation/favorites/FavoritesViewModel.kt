package com.obi.moviecompose.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.obi.moviecompose.domain.DomainMovie
import com.obi.moviecompose.domain.usecases.GetFavoriteMoviesUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import logcat.logcat

class FavoritesViewModel(
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase
) :
    ViewModel() {
    private var movies: List<DomainMovie> = emptyList()
    private val _displayedMovies: MutableStateFlow<MutableList<DomainMovie>> =
        MutableStateFlow(mutableListOf())
    val displayedMovies: MutableStateFlow<MutableList<DomainMovie>> = _displayedMovies

    private val _searchText: MutableStateFlow<String> = MutableStateFlow("")
    val searchText: MutableStateFlow<String> = _searchText

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isLoading: MutableStateFlow<Boolean> = _isLoading

    init {
        getFavoriteMovies(false)
    }

    fun getFavoriteMovies(isRefresh: Boolean) {
        if (isRefresh) {
            _isLoading.value = true
        }
        _displayedMovies.value.clear()
        viewModelScope.launch {
            getFavoriteMoviesUseCase()
                .onSuccess { result ->
                    delay(500)
                    _isLoading.value = false
                    movies = result.movies.orEmpty()
                    _displayedMovies.value = movies.toMutableList()
                }
                .onFailure { e ->
                    _isLoading.value = false
                    logcat { e.message.toString() }
                }
        }
    }

    fun onSearchTextChanged(text: String) {
        _searchText.value = text
        if (text.isBlank()) {
            _displayedMovies.value = movies.toMutableList()
        } else {
            _displayedMovies.value =
                movies.filter { it.title.contains(text, ignoreCase = true) }.toMutableList()
        }
    }


}