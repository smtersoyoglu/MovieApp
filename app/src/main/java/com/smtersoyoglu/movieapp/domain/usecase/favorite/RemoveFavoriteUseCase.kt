package com.smtersoyoglu.movieapp.domain.usecase.favorite

import com.smtersoyoglu.movieapp.domain.repository.FavoriteRepository
import javax.inject.Inject

class RemoveFavoriteUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    suspend operator fun invoke(movieId: Int) {
        favoriteRepository.deleteFavorite(movieId)
    }
}