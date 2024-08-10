package com.obi.moviecompose.presentation.favorites

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.obi.moviecompose.domain.DomainMovie
import com.obi.moviecompose.domain.usecases.GetFavoriteMoviesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import logcat.logcat

class FavoritesViewModel(private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase) :
    ViewModel(), DefaultLifecycleObserver {
    private var movies: List<DomainMovie> = emptyList()
    private val _displayedMovies: MutableStateFlow<List<DomainMovie>> =
        MutableStateFlow(listOf())
    val displayedMovies: MutableStateFlow<List<DomainMovie>> = _displayedMovies

    private val _searchText: MutableStateFlow<String> = MutableStateFlow("")
    val searchText: MutableStateFlow<String> = _searchText

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isLoading: MutableStateFlow<Boolean> = _isLoading

    init {
        getFavoriteMovies()
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        _isLoading.value = true
        getFavoriteMovies()
    }

    fun getFavoriteMovies() {
        viewModelScope.launch {
            getFavoriteMoviesUseCase()
                .onSuccess { result ->
                    _isLoading.value = false
                    movies = result.movies.orEmpty()
                    _displayedMovies.value = movies
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
            _displayedMovies.value = movies
        } else {
            _displayedMovies.value = movies.filter { it.title.contains(text, ignoreCase = true) }
        }
    }


}