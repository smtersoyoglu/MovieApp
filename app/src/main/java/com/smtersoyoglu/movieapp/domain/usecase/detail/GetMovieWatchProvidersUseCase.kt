package com.smtersoyoglu.movieapp.domain.usecase.detail

import com.smtersoyoglu.movieapp.common.Resource
import com.smtersoyoglu.movieapp.domain.model.movie.MovieWatchProviders
import com.smtersoyoglu.movieapp.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieWatchProvidersUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    suspend operator fun invoke(movieId: Int): Resource<MovieWatchProviders> {
        return movieRepository.getMovieWatchProviders(movieId)
    }
}