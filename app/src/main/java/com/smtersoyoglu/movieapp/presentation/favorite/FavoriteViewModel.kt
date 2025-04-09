package com.smtersoyoglu.movieapp.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtersoyoglu.movieapp.domain.model.FavoriteMovie
import com.smtersoyoglu.movieapp.domain.usecase.GetFavoriteMoviesUseCase
import com.smtersoyoglu.movieapp.domain.usecase.RemoveFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteUseCase
) : ViewModel(){

    private val _uiState = MutableStateFlow(FavoriteUiState())
    val uiState: StateFlow<FavoriteUiState> = _uiState.asStateFlow()

    init {
        getFavoriteMovies()
    }

    private fun getFavoriteMovies() {
        viewModelScope.launch {
            try {
                updateState {copy(isLoading = true) }
                getFavoriteMoviesUseCase().collect { favoriteMovies ->
                    updateState { copy(isLoading = false, favorites = favoriteMovies, error = null) }
                }
            } catch (e: Exception) {
                updateState{ copy(isLoading = false, error = "Failed to load favorites: ${e.message}") }
            }
        }
    }

    fun removeFavorite(favoriteMovie: FavoriteMovie) {
        viewModelScope.launch {
            try {
                removeFavoriteUseCase(favoriteMovie)
                updateState { copy(message = "Movie removed from favorites") }
            } catch (e: Exception) {
                updateState { copy(error = "Error removing favorite: ${e.message}") }
            }
        }
    }

    fun clearMessage() {
        updateState { copy(message = null) }
    }

    private fun updateState(block: FavoriteUiState.() -> FavoriteUiState) {
        _uiState.update(block)
    }

}