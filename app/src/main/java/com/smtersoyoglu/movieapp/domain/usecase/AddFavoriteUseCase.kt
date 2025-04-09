package com.smtersoyoglu.movieapp.domain.usecase

import com.smtersoyoglu.movieapp.domain.model.FavoriteMovie
import com.smtersoyoglu.movieapp.domain.repository.FavoriteRepository
import javax.inject.Inject

class AddFavoriteUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    suspend operator fun invoke(favoriteMovie: FavoriteMovie) {
        favoriteRepository.insertFavorite(favoriteMovie)
    }
}