package com.smtersoyoglu.movieapp.data.mapper

import com.smtersoyoglu.movieapp.data.source.remote.dto.MovieImageDto
import com.smtersoyoglu.movieapp.data.source.remote.dto.MovieImagesDto
import com.smtersoyoglu.movieapp.domain.model.MovieImage
import com.smtersoyoglu.movieapp.domain.model.MovieImages

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