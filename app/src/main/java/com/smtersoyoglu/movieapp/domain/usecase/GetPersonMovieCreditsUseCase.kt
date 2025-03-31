package com.smtersoyoglu.movieapp.domain.usecase

import com.smtersoyoglu.movieapp.common.Resource
import com.smtersoyoglu.movieapp.domain.model.PersonMovieCredits
import com.smtersoyoglu.movieapp.domain.repository.MovieRepository
import javax.inject.Inject

class GetPersonMovieCreditsUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    suspend operator fun invoke(personId: Int, language: String = "en-US") : Resource<PersonMovieCredits> {
        return movieRepository.getPersonMovieCredits(personId, language)
    }

}