package com.smtersoyoglu.movieapp.data.repository

import com.smtersoyoglu.movieapp.data.mapper.favorite.toFavoriteDomain
import com.smtersoyoglu.movieapp.data.mapper.favorite.toFavoriteEntity
import com.smtersoyoglu.movieapp.data.source.local.FavoriteDao
import com.smtersoyoglu.movieapp.domain.model.favorite.FavoriteMovie
import com.smtersoyoglu.movieapp.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val favoriteDao: FavoriteDao,
) : FavoriteRepository {

    override suspend fun insertFavorite(favoriteMovie: FavoriteMovie) {
        favoriteDao.insertFavorite(favoriteMovie.toFavoriteEntity())
    }

    override suspend fun deleteFavorite(movieId: Int) {
        favoriteDao.deleteFavoriteById(movieId)
    }

    override fun getAllFavorites(): Flow<List<FavoriteMovie>> =
        favoriteDao.getAllFavorites().map { entities -> entities.map { it.toFavoriteDomain() } }

    override fun isFavorite(movieId: Int): Flow<Boolean> = favoriteDao.isFavorite(movieId)

}