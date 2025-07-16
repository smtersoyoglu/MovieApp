package com.smtersoyoglu.movieapp.data.mapper.movie

import com.smtersoyoglu.movieapp.data.source.remote.dto.MovieDto
import com.smtersoyoglu.movieapp.domain.model.movie.Movie

fun MovieDto.toMovie(): Movie {
    return Movie(
        id = id,
        title = title ?: "Unknown Title",
        overview = overview ?: "No overview available",
        posterPath = posterPath,
        backdropPath = backdropPath,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        adult = adult,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        popularity = popularity,
        video = video,
        voteCount = voteCount,
        genreIds = genreIds
    )
}