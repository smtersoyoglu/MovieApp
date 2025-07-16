package com.smtersoyoglu.movieapp.domain.usecase.detail

import com.smtersoyoglu.movieapp.common.Resource
import com.smtersoyoglu.movieapp.domain.model.movie.MovieImages
import com.smtersoyoglu.movieapp.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieImagesUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    suspend operator fun invoke(movieId: Int) : Resource<MovieImages> {
        return movieRepository.getMovieImages(movieId)
    }
}