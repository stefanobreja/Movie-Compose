package com.obi.moviecompose.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.obi.moviecompose.domain.Movie
import com.obi.moviecompose.domain.usecases.SearchMovieUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchMovieUseCase: SearchMovieUseCase
) : ViewModel() {
    val searchText = MutableStateFlow("")
    val movies = MutableStateFlow<List<Movie>>(emptyList())
    val isLoading = MutableStateFlow(false)

    fun onSearchTextChanged(text: String) {
        isLoading.value = true
        searchText.value = text
        viewModelScope.launch {
            delay(2000)
            searchMovieUseCase.invoke(SearchMovieUseCase.Params(text))
                .onSuccess {
                    isLoading.value = false
                    movies.value = it.movies
                }
                .onFailure {
                    isLoading.value = false
                }
        }
    }
}