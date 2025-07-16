package com.smtersoyoglu.movieapp.data.mapper.movie

import com.smtersoyoglu.movieapp.data.source.remote.dto.MovieVideoDto
import com.smtersoyoglu.movieapp.data.source.remote.dto.MovieVideosResponseDto
import com.smtersoyoglu.movieapp.domain.model.movie.MovieVideo
import com.smtersoyoglu.movieapp.domain.model.movie.MovieVideos

fun MovieVideosResponseDto.toMovieVideos(): MovieVideos {
    return MovieVideos(id, results.map { it.toVideo() })
}

fun MovieVideoDto.toVideo(): MovieVideo {
    return MovieVideo(
        id = id,
        name = name,
        key = key,
        site = site,
        type = type,
        official = official,
        publishedAt = publishedAt,
        languageCode = iso6391,
        countryCode = iso31661
    )
}