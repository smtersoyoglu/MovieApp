package com.smtersoyoglu.movieapp.domain.usecase.detail

import com.smtersoyoglu.movieapp.common.Resource
import com.smtersoyoglu.movieapp.domain.model.movie.MovieVideos
import com.smtersoyoglu.movieapp.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieVideosUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    suspend operator fun invoke(movieId: Int, language: String = "en-US"): Resource<MovieVideos> {
        return movieRepository.getMovieVideos(movieId, language)
    }
}