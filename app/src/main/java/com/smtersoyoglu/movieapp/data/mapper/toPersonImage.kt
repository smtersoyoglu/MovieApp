package com.smtersoyoglu.movieapp.data.mapper

import com.smtersoyoglu.movieapp.data.source.remote.dto.PersonImageDto
import com.smtersoyoglu.movieapp.data.source.remote.dto.PersonImagesDto
import com.smtersoyoglu.movieapp.domain.model.PersonImage

fun PersonImagesDto.toPersonImages(): List<PersonImage> {
    return profiles.map { it.toPersonImage() }
}

fun PersonImageDto.toPersonImage(): PersonImage {
    return PersonImage(
        filePath = filePath,
        voteAverage = voteAverage,
        voteCount = voteCount
    )
}