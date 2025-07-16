package com.smtersoyoglu.movieapp.domain.usecase.favorite

import com.smtersoyoglu.movieapp.domain.model.favorite.FavoriteMovie
import com.smtersoyoglu.movieapp.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteMoviesUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    operator fun invoke() : Flow<List<FavoriteMovie>> {
        return favoriteRepository.getAllFavorites()
    }
}