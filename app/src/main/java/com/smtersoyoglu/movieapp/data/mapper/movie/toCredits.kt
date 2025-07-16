package com.smtersoyoglu.movieapp.data.mapper.movie

import com.smtersoyoglu.movieapp.data.source.remote.dto.CastDto
import com.smtersoyoglu.movieapp.data.source.remote.dto.CrewDto
import com.smtersoyoglu.movieapp.data.source.remote.dto.MovieCreditsDto
import com.smtersoyoglu.movieapp.domain.model.movie.Cast
import com.smtersoyoglu.movieapp.domain.model.movie.Crew
import com.smtersoyoglu.movieapp.domain.model.movie.MovieCredits

fun MovieCreditsDto.toCredits(): MovieCredits {
    return MovieCredits(
        id = id,
        cast = cast.map { it.toCast() },
        crew = crew.map { it.toCrew() }
    )
}

fun CastDto.toCast(): Cast {
    return Cast(
        id = id,
        name = name,
        character = character,
        profilePath = profilePath
    )
}

fun CrewDto.toCrew(): Crew {
    return Crew(
        id = id,
        name = name,
        department = department,
        job = job,
        profilePath = profilePath
    )
}