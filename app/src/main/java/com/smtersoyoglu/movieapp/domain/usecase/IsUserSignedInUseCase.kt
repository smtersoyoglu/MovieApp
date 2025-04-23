package com.smtersoyoglu.movieapp.domain.usecase

import com.smtersoyoglu.movieapp.domain.repository.AuthRepository
import javax.inject.Inject

class IsUserSignedInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Boolean {
        return authRepository.isUserSignedIn()
    }
}