package com.smtersoyoglu.movieapp.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.smtersoyoglu.movieapp.data.source.local.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}