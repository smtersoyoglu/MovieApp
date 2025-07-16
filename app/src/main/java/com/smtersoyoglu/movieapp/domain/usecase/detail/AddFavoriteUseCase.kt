package com.smtersoyoglu.movieapp.domain.usecase.detail

import com.smtersoyoglu.movieapp.domain.model.favorite.FavoriteMovie
import com.smtersoyoglu.movieapp.domain.repository.FavoriteRepository
import javax.inject.Inject

class AddFavoriteUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    suspend operator fun invoke(favoriteMovie: FavoriteMovie) {
        favoriteRepository.insertFavorite(favoriteMovie)
    }
}