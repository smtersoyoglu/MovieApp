package com.smtersoyoglu.movieapp.domain.usecase.person

import com.smtersoyoglu.movieapp.common.Resource
import com.smtersoyoglu.movieapp.domain.model.person.PersonImage
import com.smtersoyoglu.movieapp.domain.repository.MovieRepository
import javax.inject.Inject

class GetPersonImagesUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    suspend operator fun invoke(personId: Int): Resource<List<PersonImage>> {
        return movieRepository.getPersonImages(personId)
    }
}