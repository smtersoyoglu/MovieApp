package com.smtersoyoglu.movieapp.data.mapper.person

import com.smtersoyoglu.movieapp.data.source.remote.dto.PersonExternalIdsDto
import com.smtersoyoglu.movieapp.domain.model.person.PersonExternalIds

fun PersonExternalIdsDto.toPersonExternalIds(): PersonExternalIds {
    return PersonExternalIds(
        imdbId = imdbId,
        instagramId = instagramId,
        twitterId = twitterId,
    )
}