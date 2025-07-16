package com.smtersoyoglu.movieapp.domain.usecase.detail

import com.smtersoyoglu.movieapp.domain.repository.FavoriteRepository
import javax.inject.Inject

class IsFavoriteUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    suspend operator fun invoke(movieId: Int): Boolean {
        return favoriteRepository.isFavorite(movieId)
    }
}