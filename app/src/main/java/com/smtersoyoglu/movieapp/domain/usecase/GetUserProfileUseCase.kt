package com.smtersoyoglu.movieapp.domain.usecase

import com.smtersoyoglu.movieapp.common.Resource
import com.smtersoyoglu.movieapp.domain.model.UserProfile
import com.smtersoyoglu.movieapp.domain.repository.AuthRepository
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Resource<UserProfile> {
        return authRepository.getUserProfile()
    }
}