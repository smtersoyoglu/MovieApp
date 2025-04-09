package com.smtersoyoglu.movieapp.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.smtersoyoglu.movieapp.data.source.local.entity.FavoriteMovieEntity

@Database(entities = [FavoriteMovieEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}