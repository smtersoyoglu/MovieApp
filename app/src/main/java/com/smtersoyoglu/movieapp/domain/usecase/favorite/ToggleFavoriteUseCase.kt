package com.smtersoyoglu.movieapp.domain.usecase.favorite

import com.smtersoyoglu.movieapp.common.Resource
import com.smtersoyoglu.movieapp.data.mapper.favorite.toFavoriteMovie
import com.smtersoyoglu.movieapp.domain.model.movie.MovieDetails
import com.smtersoyoglu.movieapp.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    suspend operator fun invoke(details: MovieDetails): Resource<Boolean> {
        return try {
            val isFavorite = favoriteRepository.isFavorite(details.id).first()
            if (isFavorite) {
                favoriteRepository.deleteFavorite(details.id)
                Resource.Success(false)
            } else {
                favoriteRepository.insertFavorite(details.toFavoriteMovie())
                Resource.Success(true)
            }
        } catch (e: Exception) {
            Resource.Error("Failed to toggle favorite: ${e.message}")
        }
    }
}