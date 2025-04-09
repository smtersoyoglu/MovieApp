package com.smtersoyoglu.movieapp.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.smtersoyoglu.movieapp.common.Resource
import com.smtersoyoglu.movieapp.domain.model.FavoriteMovie
import com.smtersoyoglu.movieapp.domain.usecase.AddFavoriteUseCase
import com.smtersoyoglu.movieapp.domain.usecase.GetMovieCreditsUseCase
import com.smtersoyoglu.movieapp.domain.usecase.GetMovieDetailsUseCase
import com.smtersoyoglu.movieapp.domain.usecase.GetMovieImagesUseCase
import com.smtersoyoglu.movieapp.domain.usecase.GetMovieVideosUseCase
import com.smtersoyoglu.movieapp.domain.usecase.GetSimilarMoviesUseCase
import com.smtersoyoglu.movieapp.domain.usecase.IsFavoriteUseCase
import com.smtersoyoglu.movieapp.domain.usecase.RemoveFavoriteUseCase
import com.smtersoyoglu.movieapp.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getMovieCreditsUseCase: GetMovieCreditsUseCase,
    private val getMovieVideosUseCase: GetMovieVideosUseCase,
    private val getSimilarMoviesUseCase: GetSimilarMoviesUseCase,
    private val getMovieImagesUseCase: GetMovieImagesUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteUseCase,
    private val isFavoriteUseCase: IsFavoriteUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    private val args = savedStateHandle.toRoute<Screen.Detail>()

    init {
        getMovieDetails(args.movieId)
        getMovieCredits(args.movieId)
        getMovieVideos(args.movieId)
        getSimilarMovies(args.movieId)
        getMovieImages(args.movieId)
        checkFavoriteStatus(args.movieId)
    }

    private fun getMovieDetails(movieId: Int) {
        viewModelScope.launch {
            updateState { copy(isLoading = true) }
            when (val result = getMovieDetailsUseCase(movieId)) {
                is Resource.Success -> {
                    updateState { copy(isLoading = false, movieDetails = result.data, error = null) }
                }
                is Resource.Error -> {
                    updateState { copy(isLoading = false, error = result.message) }
                }
            }
        }
    }

    private fun getMovieCredits(movieId: Int) {
        viewModelScope.launch {
            updateState { copy(isLoading = true) }
            when (val result = getMovieCreditsUseCase(movieId)) {
                is Resource.Success -> {
                    updateState { copy(isLoading = false, movieCredits = result.data, error = null) }
                }
                is Resource.Error -> {
                    updateState { copy(isLoading = false, error = result.message) }
                }
            }
        }

    }

    private fun getMovieVideos(movieId: Int) {
        viewModelScope.launch {
            updateState { copy(isLoading = true) }
            when (val result = getMovieVideosUseCase(movieId)) {
                is Resource.Success -> {
                    updateState { copy(isLoading = false, movieVideos = result.data, error = null) }
                }
                is Resource.Error -> {
                    updateState { copy(isLoading = false, error = result.message) }
                }
            }
        }

    }

    private fun getSimilarMovies(movieId: Int) {
        viewModelScope.launch {
            updateState { copy(isLoading = true) }
            when (val result = getSimilarMoviesUseCase(movieId)) {
                is Resource.Success -> {
                    updateState { copy(isLoading = false, similarMovies = result.data ?: emptyList(), error = null) }
                }
                is Resource.Error -> {
                    updateState { copy(isLoading = false, error = result.message) }
                }
            }
        }
    }

    private fun getMovieImages(movieId: Int) {
        viewModelScope.launch {
            updateState { copy(isLoading = true) }
            when (val result = getMovieImagesUseCase(movieId)) {
                is Resource.Success -> {
                    updateState { copy(isLoading = false, movieImages = result.data, error = null) }
                }
                is Resource.Error -> {
                    updateState { copy(isLoading = false, error = result.message) }
                }
            }
        }
    }

    fun selectTab(tabIndex: Int) {
        _uiState.update { it.copy(selectedTab = tabIndex) }
    }

    private fun checkFavoriteStatus(movieId: Int) {
        viewModelScope.launch {
            val isFavorite = isFavoriteUseCase(movieId)
            updateState { copy(isFavorite = isFavorite) }
        }
    }

    fun toggleFavorite(favoriteMovie: FavoriteMovie) {
        viewModelScope.launch {
            if (_uiState.value.isFavorite) {
                removeFavoriteUseCase(favoriteMovie)
                updateState { copy(isFavorite = false, message = "Removed from favorites") }
            } else {
                addFavoriteUseCase(favoriteMovie)
                updateState { copy(isFavorite = true, message = "Added to favorites") }
            }
        }
    }

    fun clearMessage() {
        updateState { copy(message = null) }
    }

    private fun updateState(block: DetailUiState.() -> DetailUiState) {
        _uiState.update(block)
    }
}