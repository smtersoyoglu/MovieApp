package com.smtersoyoglu.movieapp.data.mapper.person

import com.smtersoyoglu.movieapp.data.source.remote.dto.CastDto
import com.smtersoyoglu.movieapp.data.source.remote.dto.MovieCreditsDto
import com.smtersoyoglu.movieapp.domain.model.person.PersonMovieCast
import com.smtersoyoglu.movieapp.domain.model.person.PersonMovieCredits

fun MovieCreditsDto.toPersonMovieCredits(): PersonMovieCredits {
    return PersonMovieCredits(
        cast = cast.map { it.toPersonMovieCast() }
    )
}

fun CastDto.toPersonMovieCast(): PersonMovieCast {
    return PersonMovieCast(
        id = id,
        title = title ?: "Bilinmiyor",
        posterPath = posterPath,
        releaseDate = releaseDate,
        character = character
    )
}