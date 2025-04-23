package com.smtersoyoglu.movieapp.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtersoyoglu.movieapp.common.Resource
import com.smtersoyoglu.movieapp.domain.repository.AuthRepository
import com.smtersoyoglu.movieapp.domain.usecase.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEmailChanged(email: String) {
        _uiState.update { it.copy(email = email, error = null) }
    }

    fun onPasswordChanged(password: String) {
        _uiState.update { it.copy(password = password, error = null) }
    }

    fun signIn() {
        val currentState = _uiState.value
        val email = currentState.email
        val password = currentState.password

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            updateUiState { copy(error = "Invalid email format") }
            return
        }
        if (password.length < 6) {
            updateUiState { copy(error = "Password must be at least 6 characters") }
            return
        }

        viewModelScope.launch {
            updateUiState { copy(isLoading = true, error = null) }
            when (val result = signInUseCase(email, password)) {
                is Resource.Success -> updateUiState { copy(isLoading = false, isLoggedIn = true) }
                is Resource.Error -> updateUiState {
                    copy(
                        isLoading = false,
                        error = result.message
                    )
                }
            }
        }
    }

    private fun updateUiState(block: LoginUiState.() -> LoginUiState) {
        _uiState.update(block)
    }
}