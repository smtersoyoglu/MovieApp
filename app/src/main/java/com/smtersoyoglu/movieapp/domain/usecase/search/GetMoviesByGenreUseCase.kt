package com.smtersoyoglu.movieapp.domain.usecase.search

import com.smtersoyoglu.movieapp.common.Resource
import com.smtersoyoglu.movieapp.domain.model.movie.Movie
import com.smtersoyoglu.movieapp.domain.repository.MovieRepository
import javax.inject.Inject

class GetMoviesByGenreUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    suspend operator fun invoke(
        genreId: Int,
        page: Int = 1,
        language: String = "en-US",
    ): Resource<List<Movie>> {
        return movieRepository.getMoviesByGenre(genreId, page, language)
    }
}