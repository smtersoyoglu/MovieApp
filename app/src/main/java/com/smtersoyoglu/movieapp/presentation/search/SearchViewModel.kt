package com.smtersoyoglu.movieapp.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.smtersoyoglu.movieapp.common.Resource
import com.smtersoyoglu.movieapp.domain.model.movie.Movie
import com.smtersoyoglu.movieapp.domain.usecase.search.GetGenresUseCase
import com.smtersoyoglu.movieapp.domain.usecase.search.GetMoviesByGenreUseCase
import com.smtersoyoglu.movieapp.domain.usecase.search.GetSearchMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchMoviesUseCase: GetSearchMoviesUseCase,
    private val getGenresUseCase: GetGenresUseCase,
    private val getMoviesByGenreUseCase: GetMoviesByGenreUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private val searchQuery = MutableStateFlow("")


    @OptIn(ExperimentalCoroutinesApi::class)
    val searchMoviesPaging: Flow<PagingData<Movie>> = searchQuery
        .debounce(500)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            updateUiState { copy(query = query) }
            if (query.isNotBlank()) {
                getSearchMoviesUseCase(query)
            } else {
                flowOf(PagingData.empty())
            }
        }
        .cachedIn(viewModelScope)

    init {
        loadGenres()
    }

    fun updateSearchQuery(query: String) {
        searchQuery.value = query
        if (query.isNotBlank()) {
            updateUiState { copy(selectedGenreId = null, genreMovies = emptyList()) }
        }
    }

    fun selectGenre(genreId: Int) {
        searchQuery.value = ""
        updateUiState { copy(selectedGenreId = genreId, query = "") }
        loadMoviesByGenre(genreId)
    }

    private fun loadGenres() {
        viewModelScope.launch {
            updateUiState { copy(isLoading = true) }
            when (val result = getGenresUseCase()) {
                is Resource.Success -> {
                    val genres = result.data ?: emptyList()
                    updateUiState { copy(genres = genres, isLoading = false, error = null) }
                    genres.firstOrNull()?.let { genre ->
                        selectGenre(genre.id)
                    }
                }
                is Resource.Error -> {
                    updateUiState { copy(isLoading = false, error = result.message ?: "Unknown error occurred") }
                }
            }
        }
    }

    private fun loadMoviesByGenre(genreId: Int) {
        viewModelScope.launch {
            updateUiState { copy(isLoading = true) }

            when (val result = getMoviesByGenreUseCase(genreId)) {
                is Resource.Success -> {
                    updateUiState { copy(isLoading = false, genreMovies = result.data ?: emptyList(), error = null) }
                }
                is Resource.Error -> {
                    updateUiState { copy(isLoading = false, error = result.message ?: "Unknown error occurred") }
                }
            }
        }
    }

    fun retry() {
        if (searchQuery.value.isNotBlank()) {
            val currentQuery = searchQuery.value
            searchQuery.value = ""
            searchQuery.value = currentQuery
        } else {
            uiState.value.selectedGenreId?.let { genreId ->
                loadMoviesByGenre(genreId)
            }
        }
    }

    private fun updateUiState(block: SearchUiState.() -> SearchUiState) {
        _uiState.update(block)
    }
}