package com.smtersoyoglu.movieapp.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.smtersoyoglu.movieapp.data.source.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(movieEntity: MovieEntity)

    @Delete
    suspend fun deleteFavorite(movieEntity: MovieEntity)

    @Query("SELECT * FROM favorites")
    fun getAllFavorites(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM favorites WHERE id = :movieId")
    suspend fun getFavoriteById(movieId: Int): MovieEntity?

}