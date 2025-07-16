package com.smtersoyoglu.movieapp.presentation.search

import com.smtersoyoglu.movieapp.domain.model.movie.Genre
import com.smtersoyoglu.movieapp.domain.model.movie.Movie

data class SearchUiState(
    val isLoading: Boolean = false,
    val movies: List<Movie> = emptyList(),
    val totalPages: Int = 0,
    val currentPage: Int = 1,
    val query: String = "",
    val genres: List<Genre> = emptyList(),
    val selectedGenreId: Int? = null,
    val error: String? = null
)