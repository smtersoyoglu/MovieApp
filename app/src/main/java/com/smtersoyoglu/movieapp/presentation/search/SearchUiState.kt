package com.smtersoyoglu.movieapp.presentation.search

import com.smtersoyoglu.movieapp.domain.model.Movie

data class SearchUiState(
    val isLoading: Boolean = false,
    val movies: List<Movie> = emptyList(),
    val totalPages: Int = 0,
    val currentPage: Int = 1,
    val query: String = "",
    val error: String? = null
)