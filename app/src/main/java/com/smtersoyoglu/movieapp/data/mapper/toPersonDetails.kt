package com.smtersoyoglu.movieapp.data.mapper

import com.smtersoyoglu.movieapp.data.source.remote.dto.PersonDetailsDto
import com.smtersoyoglu.movieapp.domain.model.PersonDetails

fun PersonDetailsDto.toPersonDetails(): PersonDetails {
    return PersonDetails(
        id = id,
        name = name,
        biography = biography,
        birthday = birthday,
        deathday = deathday,
        gender = gender,
        knownForDepartment = knownForDepartment ,
        placeOfBirth = placeOfBirth,
        popularity = popularity,
        profilePath = profilePath,
        alsoKnownAs = alsoKnownAs,
        imdbId = imdbId
    )
}