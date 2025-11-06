package com.smtersoyoglu.movieapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.smtersoyoglu.movieapp.common.NetworkUtils
import com.smtersoyoglu.movieapp.common.Resource
import com.smtersoyoglu.movieapp.domain.model.movie.Movie
import com.smtersoyoglu.movieapp.domain.usecase.home.GetNowPlayingMoviesUseCase
import com.smtersoyoglu.movieapp.domain.usecase.home.GetPopularMoviesUseCase
import com.smtersoyoglu.movieapp.domain.usecase.home.GetTopRatedMoviesUseCase
import com.smtersoyoglu.movieapp.domain.usecase.home.GetTrendingMoviesUseCase
import com.smtersoyoglu.movieapp.domain.usecase.home.GetUpcomingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase,
    getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    getPopularMoviesUseCase: GetPopularMoviesUseCase,
    getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val networkUtils: NetworkUtils
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    val nowPlayingMoviesPaging: Flow<PagingData<Movie>> =
        getNowPlayingMoviesUseCase().cachedIn(viewModelScope)

    val popularMoviesPaging: Flow<PagingData<Movie>> =
        getPopularMoviesUseCase().cachedIn(viewModelScope)

    val topRatedMoviesPaging: Flow<PagingData<Movie>> =
        getTopRatedMoviesUseCase().cachedIn(viewModelScope)

    val upcomingMoviesPaging: Flow<PagingData<Movie>> =
        getUpcomingMoviesUseCase().cachedIn(viewModelScope)

    init {
        observeNetwork()
        loadTrendingMovies()
    }

    private fun observeNetwork() {
        networkUtils.observeNetworkState()
            .onEach { isConnected ->
                if (isConnected && _uiState.value.isNoInternet) {
                    updateState { copy(isNoInternet = false, error = null) }
                    loadTrendingMovies()
                } else if (!isConnected) {
                    updateState { copy(isNoInternet = true) }
                }
            }.launchIn(viewModelScope)
    }

    private fun loadTrendingMovies() {
        updateState { copy(isLoading = true) }
        getTrendingMoviesUseCase()
            .onEach { resource ->
                when (resource) {
                    is Resource.Success -> {
                        updateState {
                            copy( isLoading = false, trendingMovieList = resource.data ?: emptyList(), error = null)
                        }
                    }
                    is Resource.Error -> {
                        updateState {
                            copy( isLoading = false, error = resource.message ?: "Unknown error")
                        }
                    }
                }
            }.launchIn(viewModelScope)
    }

    fun retry() {
        if (networkUtils.isNetworkAvailable()) {
            updateState { copy(isNoInternet = false, error = null) }
            loadTrendingMovies()
        } else { updateState { copy(isNoInternet = true) } }
    }

    private inline fun updateState(block: HomeUiState.() -> HomeUiState) {
        _uiState.update(block)
    }
}