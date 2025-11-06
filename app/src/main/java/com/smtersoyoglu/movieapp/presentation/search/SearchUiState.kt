package com.smtersoyoglu.movieapp.presentation.search

import com.smtersoyoglu.movieapp.domain.model.movie.Genre
import com.smtersoyoglu.movieapp.domain.model.movie.Movie

data class SearchUiState(
    val isLoading: Boolean = false,
    val genres: List<Genre> = emptyList(),
    val selectedGenreId: Int? = null,
    val error: String? = null,
    val query: String = "",
    val genreMovies: List<Movie> = emptyList()
)