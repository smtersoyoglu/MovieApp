package com.smtersoyoglu.movieapp.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtersoyoglu.movieapp.common.Resource
import com.smtersoyoglu.movieapp.domain.usecase.GetGenresUseCase
import com.smtersoyoglu.movieapp.domain.usecase.GetMoviesByGenreUseCase
import com.smtersoyoglu.movieapp.domain.usecase.GetSearchMoviesUseCase
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

    private val searchQueryFlow = MutableStateFlow("")

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    init {
        getGenres()
        viewModelScope.launch {
            searchQueryFlow
                .debounce(500)
                .collectLatest { query ->
                    if (query.isNotBlank()) {
                        getSearchMovies(query)
                    } else {
                        updateUiState { copy(movies = emptyList(), error = null) }
                        uiState.value.genres.firstOrNull()?.let { getMoviesByGenre(it.id) }
                    }
                }
        }
    }

    fun updateSearchQuery(query: String) {
        searchQueryFlow.value = query
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
            when (val result = getGenresUseCase()) {
                is Resource.Success -> {
                    updateUiState { copy(genres = result.data ?: emptyList()) }
                    result.data?.firstOrNull()?.let { getMoviesByGenre(it.id) }
                }

                is Resource.Error -> {
                    updateUiState { copy(error = result.message) }
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