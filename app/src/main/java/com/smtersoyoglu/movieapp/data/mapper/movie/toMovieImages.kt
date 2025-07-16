package com.smtersoyoglu.movieapp.data.mapper.movie

import com.smtersoyoglu.movieapp.data.source.remote.dto.MovieImageDto
import com.smtersoyoglu.movieapp.data.source.remote.dto.MovieImagesDto
import com.smtersoyoglu.movieapp.domain.model.movie.MovieImage
import com.smtersoyoglu.movieapp.domain.model.movie.MovieImages

fun MovieImagesDto.toMovieImages(): MovieImages {
    return MovieImages(
        backdrops = backdrops.map { it.toMovieImage() },
        posters = posters.map { it.toMovieImage() }
    )
}

fun MovieImageDto.toMovieImage(): MovieImage {
    return MovieImage(
        filePath = filePath,
        voteAverage = voteAverage,
        voteCount = voteCount
    )
}