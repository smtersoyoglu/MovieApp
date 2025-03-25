package com.smtersoyoglu.movieapp.domain.model

data class MovieVideos(
    val id: Int,
    val videos: List<MovieVideo>
)

data class MovieVideo(
    val id: String,
    val name: String,
    val key: String,
    val site: String,
    val type: String,
    val official: Boolean,
    val publishedAt: String,
    val languageCode: String,
    val countryCode: String
)