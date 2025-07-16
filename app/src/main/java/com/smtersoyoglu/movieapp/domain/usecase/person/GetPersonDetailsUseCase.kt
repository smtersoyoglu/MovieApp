package com.smtersoyoglu.movieapp.domain.usecase.person

import com.smtersoyoglu.movieapp.common.Resource
import com.smtersoyoglu.movieapp.domain.model.person.PersonDetails
import com.smtersoyoglu.movieapp.domain.repository.MovieRepository
import javax.inject.Inject

class GetPersonDetailsUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    suspend operator fun invoke(
        personId: Int,
        appendToResponse: String? = null,
        language: String = "en-US",
    ): Resource<PersonDetails> {
        return movieRepository.getPersonDetails(personId, appendToResponse, language)
    }
}