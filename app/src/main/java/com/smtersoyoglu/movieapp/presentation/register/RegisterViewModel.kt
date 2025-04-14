package com.smtersoyoglu.movieapp.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtersoyoglu.movieapp.common.Resource
import com.smtersoyoglu.movieapp.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun register(email: String, password: String, confirmPassword: String) {
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            updateUiState { copy(error = "Invalid email format") }
            return
        }
        if (password.length < 6) {
            updateUiState { copy(error = "Password must be at least 6 characters") }
            return
        }
        if (password != confirmPassword) {
            updateUiState { copy(error = "Passwords do not match") }
            return
        }

        viewModelScope.launch {
            updateUiState { copy(isLoading = true, error = null) }
            when (val result = authRepository.signUp(email, password)) {
                is Resource.Success -> updateUiState { copy(isLoading = false, isRegistered = true) }
                is Resource.Error -> updateUiState { copy(isLoading = false, error = result.message) }
            }
        }
    }

    private fun updateUiState(block: RegisterUiState.() -> RegisterUiState) {
        _uiState.update(block)
    }
}