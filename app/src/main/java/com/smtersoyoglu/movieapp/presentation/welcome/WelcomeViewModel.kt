package com.smtersoyoglu.movieapp.presentation.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtersoyoglu.movieapp.domain.usecase.IsUserSignedInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val isUserSignedInUseCase: IsUserSignedInUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(WelcomeUiState())
    val uiState: StateFlow<WelcomeUiState> = _uiState.asStateFlow()

    init {
        checkUserSignedIn()
    }

    private fun checkUserSignedIn() {
        viewModelScope.launch {
            updateUiState { copy(isLoading = true) }
            try {
                val isSignedIn = isUserSignedInUseCase()
                updateUiState { copy(isLoading = false, isUserSignedIn = isSignedIn, error = null) }

            }catch (e: Exception){
                updateUiState { copy(isLoading = false, isUserSignedIn = false, error = e.message ?: "Unknown Error") }
            }
        }
    }

    private fun updateUiState(block: WelcomeUiState.() -> WelcomeUiState) {
        _uiState.update(block)
    }
}
