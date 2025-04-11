package com.smtersoyoglu.movieapp.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteMovieEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val posterPath: String?,
    val overview: String?,
    val releaseDate: String,
    val voteAverage: Double,
    val runtime: Int?,
    val genres: String?,
    val addedDate: Long = System.currentTimeMillis()
)