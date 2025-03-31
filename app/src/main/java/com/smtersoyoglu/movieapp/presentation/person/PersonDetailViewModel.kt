package com.smtersoyoglu.movieapp.presentation.person

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.smtersoyoglu.movieapp.common.Resource
import com.smtersoyoglu.movieapp.domain.usecase.GetPersonDetailsUseCase
import com.smtersoyoglu.movieapp.domain.usecase.GetPersonMovieCreditsUseCase
import com.smtersoyoglu.movieapp.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonDetailViewModel @Inject constructor(
    private val getPersonDetailsUseCase: GetPersonDetailsUseCase,
    private val getPersonMovieCreditsUseCase: GetPersonMovieCreditsUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _uiState = MutableStateFlow(PersonUiState())
    val uiState: StateFlow<PersonUiState> = _uiState.asStateFlow()

    private val args = savedStateHandle.toRoute<Screen.Person>()

    init {
        getPersonDetails(args.personId)
        getPersonMovieCredits(args.personId)
    }

    private fun getPersonDetails(personId: Int) {
        viewModelScope.launch {
            updateState { copy(isLoading = true) }
            when (val result = getPersonDetailsUseCase(personId)) {
                is Resource.Success -> {
                    updateState { copy(isLoading = false, personDetails = result.data, error = null) }
                }
                is Resource.Error -> {
                    updateState { copy(isLoading = false, error = result.message) }
                }
            }
        }
    }

    private fun getPersonMovieCredits(personId: Int) {
        viewModelScope.launch {
            updateState { copy(isLoading = true) }
            when (val result = getPersonMovieCreditsUseCase(personId)) {
                is Resource.Success -> {
                    updateState { copy(isLoading = false, personMovieCredits = result.data, error = null) }
                }
                is Resource.Error -> {
                    updateState { copy(isLoading = false, error = result.message) }
                }
            }
        }
    }


    private fun updateState(block: PersonUiState.() -> PersonUiState) {
        _uiState.update(block)
    }
}