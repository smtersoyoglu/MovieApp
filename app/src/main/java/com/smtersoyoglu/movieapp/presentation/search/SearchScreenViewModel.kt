package com.smtersoyoglu.movieapp.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtersoyoglu.movieapp.common.Resource
import com.smtersoyoglu.movieapp.domain.usecase.GetSearchMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val getSearchMoviesUseCase: GetSearchMoviesUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()


    fun getSearchMovies(query: String, page: Int = 1) {
        viewModelScope.launch {
            updateUiState { copy(isLoading = true) }
            when (val result = getSearchMoviesUseCase(query, page)) {
                is Resource.Success -> {
                    updateUiState { copy(isLoading = false, movies = result.data ?: emptyList(), error = null) }
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