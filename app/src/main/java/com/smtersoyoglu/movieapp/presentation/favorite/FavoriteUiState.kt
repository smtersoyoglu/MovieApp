package com.smtersoyoglu.movieapp.presentation.favorite

import com.smtersoyoglu.movieapp.domain.model.favorite.FavoriteMovie

data class FavoriteUiState(
    val isLoading: Boolean = false,
    val favorites: List<FavoriteMovie> = emptyList(),
    val error: String? = null,
    val message: String? = null
)