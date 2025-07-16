package com.smtersoyoglu.movieapp.domain.usecase.search

import com.smtersoyoglu.movieapp.common.Resource
import com.smtersoyoglu.movieapp.domain.model.movie.Genre
import com.smtersoyoglu.movieapp.domain.repository.MovieRepository
import javax.inject.Inject

class GetGenresUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    suspend operator fun invoke(
        language: String = "en-US",
    ): Resource<List<Genre>> {
        return movieRepository.getGenres()
    }
}