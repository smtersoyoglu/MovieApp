package com.smtersoyoglu.movieapp.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtersoyoglu.movieapp.common.Resource
import com.smtersoyoglu.movieapp.domain.usecase.search.GetGenresUseCase
import com.smtersoyoglu.movieapp.domain.usecase.search.GetMoviesByGenreUseCase
import com.smtersoyoglu.movieapp.domain.usecase.search.GetSearchMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val getSearchMoviesUseCase: GetSearchMoviesUseCase,
    private val getGenresUseCase: GetGenresUseCase,
    private val getMoviesByGenreUseCase: GetMoviesByGenreUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private val searchQueryFlow = MutableStateFlow("")

    init {
        getGenres()
        viewModelScope.launch {
            searchQueryFlow
                .debounce(500)
                .collectLatest { query ->
                    if (query.isNotBlank()) {
                        getSearchMovies(query)
                    }
                }
        }
    }

    fun updateSearchQuery(query: String) {
        searchQueryFlow.value = query
        if (query.isBlank()) {
            uiState.value.selectedGenreId?.let { getMoviesByGenre(it) }
        }
    }

    private fun getSearchMovies(query: String, page: Int = 1) {
        viewModelScope.launch {
            updateUiState { copy(isLoading = true) }
            when (val result = getSearchMoviesUseCase(query, page)) {
                is Resource.Success -> {
                    updateUiState {
                        copy(
                            isLoading = false,
                            movies = result.data ?: emptyList(),
                            error = null,
                            query = query
                        )
                    }
                }
                is Resource.Error -> {
                    updateUiState { copy(isLoading = false, error = result.message) }
                }
            }
        }
    }

    private fun getGenres() {
        viewModelScope.launch {
            updateUiState { copy(isLoading = true) }
            when (val result = getGenresUseCase()) {
                is Resource.Success -> {
                    val genres = result.data ?: emptyList()
                    updateUiState { copy(genres = genres, isLoading = false) }
                    genres.firstOrNull()?.let { genre ->
                        updateUiState { copy(selectedGenreId = genre.id) }
                        getMoviesByGenre(genre.id)
                    }
                }
                is Resource.Error -> {
                    updateUiState { copy(isLoading = false, error = result.message) }
                }
            }
        }
    }

    fun getMoviesByGenre(genreId: Int) {
        viewModelScope.launch {
            updateUiState { copy(isLoading = true) }
            when (val result = getMoviesByGenreUseCase(genreId)) {
                is Resource.Success -> {
                    updateUiState {
                        copy(
                            isLoading = false,
                            movies = result.data ?: emptyList(),
                            error = null,
                            selectedGenreId = genreId
                        )
                    }
                }

                is Resource.Error -> {
                    updateUiState { copy(isLoading = false, error = result.message) }
                }
            }
        }
    }

    private fun updateUiState(block: SearchUiState.() -> SearchUiState) {
        _uiState.update(block)
    }
}