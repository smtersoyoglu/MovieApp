package com.smtersoyoglu.movieapp.data.mapper

import com.smtersoyoglu.movieapp.data.source.remote.dto.PersonExternalIdsDto
import com.smtersoyoglu.movieapp.domain.model.PersonExternalIds

fun PersonExternalIdsDto.toPersonExternalIds(): PersonExternalIds {
    return PersonExternalIds(
        imdbId = imdbId,
        instagramId = instagramId,
        twitterId = twitterId,
    )
}