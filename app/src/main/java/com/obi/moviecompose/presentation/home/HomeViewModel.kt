package com.obi.moviecompose.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.obi.moviecompose.domain.Movie
import com.obi.moviecompose.domain.usecases.GetAiringTodayTvShowsUseCase
import com.obi.moviecompose.domain.usecases.GetTopRatedMoviesUseCase
import com.obi.moviecompose.domain.usecases.GetTrendingMoviesUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import logcat.logcat

class HomeViewModel(
    private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getAiringTodayTvShowsUseCase: GetAiringTodayTvShowsUseCase
) : ViewModel() {
    private val _events = Channel<Event?>(Channel.BUFFERED)
    val events: Flow<Event?> = _events.receiveAsFlow()

    private var airingTodayCurrentPage = 1
    private var airingTodayTvShows: MutableList<Movie> = mutableListOf()

    private var trendingCurrentPage = 1
    private val trendingMovies: MutableList<Movie> = mutableListOf()

    private var topRatedCurrentPage = 1
    private val topRatedMovies: MutableList<Movie> = mutableListOf()

    private val _shownMovies: MutableStateFlow<MutableList<Movie>> =
        MutableStateFlow(mutableListOf())
    val shownMovies: StateFlow<List<Movie>> = _shownMovies

    private val _loadingState: MutableStateFlow<LoadingState> =
        MutableStateFlow(LoadingState(isLoading = true, isLoadingMore = false))
    val loadingState: StateFlow<LoadingState> = _loadingState

    private val _selectedTab: MutableStateFlow<TabSection> = MutableStateFlow(TabSection.Popular)
    var selectedTab: StateFlow<TabSection> = _selectedTab

    private var sortOption: SortOption? = null

    init {
        getTrendingMovies(false)
    }

    private fun getAiringTodayTvShows(isLoadMore: Boolean) {
        viewModelScope.launch {
            if (isLoadMore) {
                delay(3000)
                airingTodayCurrentPage += 1
            }

            getAiringTodayTvShowsUseCase(
                GetAiringTodayTvShowsUseCase.Params(
                    pageNumber = airingTodayCurrentPage
                )
            )
                .onSuccess { response ->
                    _loadingState.update {
                        it.copy(isLoading = false, isLoadingMore = false)
                    }
                    airingTodayTvShows += response.movies
                    _shownMovies.value = airingTodayTvShows
                    sortWithOptionIfNeeded()
                    if (!isLoadMore) {
                        _events.send(Event.AnimateTop)
                    }
                }
                .onFailure { error ->
                    logcat {
                        "getAiringTodayTvShows error: $error"
                    }
                    error.message?.let {
                        _events.send(Event.ShowError(it))
                    }
                }
        }
    }

    private fun getTrendingMovies(isLoadMore: Boolean) {
        viewModelScope.launch {
            if (isLoadMore) {
                delay(3000)
                trendingCurrentPage += 1
            }
            getTrendingMoviesUseCase(GetTrendingMoviesUseCase.Params(trendingCurrentPage))
                .onSuccess { response ->
                    _loadingState.update {
                        it.copy(isLoading = false, isLoadingMore = false)
                    }

                    trendingMovies += response.movies
                    _shownMovies.value = trendingMovies
                    sortWithOptionIfNeeded()
                    if (!isLoadMore) {
                        _events.send(Event.AnimateTop)
                    }
                }
                .onFailure { error ->
                    error.message?.let {
                        _events.send(Event.ShowError(it))
                    }
                }
        }
    }

    private fun getTopRatedMovies(isLoadMore: Boolean) {
        viewModelScope.launch {
            if (isLoadMore) {
                delay(3000)
                topRatedCurrentPage += 1
            }
            getTopRatedMoviesUseCase(GetTopRatedMoviesUseCase.Params(topRatedCurrentPage))
                .onSuccess { response ->
                    _loadingState.update {
                        it.copy(isLoading = false, isLoadingMore = false)
                    }

                    topRatedMovies += response.movies
                    _shownMovies.value = topRatedMovies
                    sortWithOptionIfNeeded()
                    if (!isLoadMore) {
                        _events.send(Event.AnimateTop)
                    }
                }
                .onFailure { error ->
                    error.message?.let {
                        _events.send(Event.ShowError(it))
                    }
                }
        }
    }

    fun onTabSelected(selectedTab: TabSection) {
        _selectedTab.value = selectedTab
        viewModelScope.launch {
            when (selectedTab) {
                TabSection.NowPlaying -> {
                    if (airingTodayTvShows.isEmpty()) {
                        getAiringTodayTvShows(false)
                    } else {
                        _shownMovies.value = airingTodayTvShows
                        _events.send(Event.AnimateTop)
                    }
                }

                TabSection.Popular -> {
                    if (trendingMovies.isEmpty()) {
                        _loadingState.update {
                            it.copy(isLoading = true)
                        }
                        getTrendingMovies(false)
                    } else {
                        _shownMovies.value = trendingMovies
                        _events.send(Event.AnimateTop)
                    }
                }

                TabSection.TopRated -> {
                    if (topRatedMovies.isEmpty()) {
                        _loadingState.update {
                            it.copy(isLoading = true)
                        }
                        getTopRatedMovies(false)
                    } else {
                        _shownMovies.value = topRatedMovies
                        _events.send(Event.AnimateTop)
                    }
                }
            }
        }
    }

    fun loadMore() {
        when (selectedTab.value) {
            TabSection.NowPlaying -> {
                _loadingState.update {
                    it.copy(isLoadingMore = true)
                }
                getAiringTodayTvShows(true)
            }

            TabSection.Popular -> {
                _loadingState.update {
                    it.copy(isLoadingMore = true)
                }
                getTrendingMovies(true)
            }

            TabSection.TopRated -> {
                _loadingState.update {
                    it.copy(isLoadingMore = true)
                }
                getTopRatedMovies(true)
            }
        }
    }

    fun onSortByRatingAscending() {
        sortOption = SortOption.RatingAscending
        _shownMovies.update {
            it.sortedBy { it.voteAverage }.toMutableList()
        }
    }

    fun onSortByRatingDescending() {
        sortOption = SortOption.RatingDescending
        _shownMovies.update {
            it.sortedByDescending { it.voteAverage }.toMutableList()
        }
    }

    fun onSortByDateAscending() {
        sortOption = SortOption.DateAscending
        _shownMovies.update {
            it.sortedBy { it.releaseDate }.toMutableList()
        }
    }

    fun onSortByDateDescending() {
        sortOption = SortOption.DateDescending
        _shownMovies.update {
            it.sortedByDescending { it.releaseDate }.toMutableList()
        }
    }

    sealed class Event {
        data class ShowError(val errorMessage: String) : Event()
        data object AnimateTop : Event()
    }

    data class LoadingState(val isLoading: Boolean, val isLoadingMore: Boolean)

    sealed class SortOption {
        data object RatingAscending : SortOption()
        data object RatingDescending : SortOption()
        data object DateAscending : SortOption()
        data object DateDescending : SortOption()
    }

    private fun sortWithOptionIfNeeded() {
        when (sortOption) {
            SortOption.DateAscending -> onSortByDateAscending()
            SortOption.DateDescending -> onSortByDateDescending()
            SortOption.RatingAscending -> onSortByRatingAscending()
            SortOption.RatingDescending -> onSortByRatingDescending()
            else -> {}
        }
    }
}