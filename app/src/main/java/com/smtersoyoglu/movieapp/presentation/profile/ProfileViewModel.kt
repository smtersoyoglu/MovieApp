package com.smtersoyoglu.movieapp.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtersoyoglu.movieapp.common.Resource
import com.smtersoyoglu.movieapp.domain.usecase.profile.GetUserProfileUseCase
import com.smtersoyoglu.movieapp.domain.usecase.profile.SignOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val signOutUseCase: SignOutUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        fetchUserProfile()
    }

    private fun fetchUserProfile() {
        viewModelScope.launch {
            updateUiState { copy(isLoading = true, error = null) }
            when (val result = getUserProfileUseCase()) {
                is Resource.Success -> updateUiState { copy(isLoading = false, userProfile = result.data) }
                is Resource.Error -> updateUiState { copy(isLoading = false, error = result.message) }
            }
        }
    }

    fun onSignOutClicked() {
        updateUiState { copy(showSignOutDialog = true) }
    }

    fun onSignOutConfirmed() {
        viewModelScope.launch {
            updateUiState{ copy(isLoading = true, showSignOutDialog = false) }
            when (val result = signOutUseCase()) {
                is Resource.Success -> updateUiState { copy(isLoading = false, isSignedOut = true) }
                is Resource.Error -> updateUiState { copy(isLoading = false, error = result.message) }
            }
        }
    }

    fun onAboutClicked() {
        updateUiState { copy(showAboutDialog = true) }
    }

    fun onAboutDialogDismissed() {
        updateUiState { copy(showAboutDialog = false) }
    }


    fun onSignOutDialogDismissed() {
        updateUiState { copy(showSignOutDialog = false) }
    }

    private fun updateUiState(block: ProfileUiState.() -> ProfileUiState) {
        _uiState.update(block)
    }

}