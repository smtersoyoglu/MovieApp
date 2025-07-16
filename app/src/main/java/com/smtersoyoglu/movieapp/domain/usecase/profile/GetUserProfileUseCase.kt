package com.smtersoyoglu.movieapp.domain.usecase.profile

import com.smtersoyoglu.movieapp.common.Resource
import com.smtersoyoglu.movieapp.domain.model.user.UserProfile
import com.smtersoyoglu.movieapp.domain.repository.AuthRepository
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Resource<UserProfile> {
        return authRepository.getUserProfile()
    }
}