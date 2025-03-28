package com.smtersoyoglu.movieapp.data.repository

import com.smtersoyoglu.movieapp.common.Resource
import com.smtersoyoglu.movieapp.data.mapper.toCredits
import com.smtersoyoglu.movieapp.data.mapper.toMovie
import com.smtersoyoglu.movieapp.data.mapper.toMovieDetails
import com.smtersoyoglu.movieapp.data.mapper.toMovieVideos
import com.smtersoyoglu.movieapp.data.source.remote.MovieService
import com.smtersoyoglu.movieapp.domain.model.Movie
import com.smtersoyoglu.movieapp.domain.model.MovieCredits
import com.smtersoyoglu.movieapp.domain.model.MovieDetails
import com.smtersoyoglu.movieapp.domain.model.MovieVideos
import com.smtersoyoglu.movieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieService: MovieService,
) : MovieRepository {


    override fun getTrendingMovies(
        timeWindow: String,
        language: String,
    ): Flow<Resource<List<Movie>>> = flow {
        try {
            val response = movieService.getTrendingMovies(timeWindow, language)
            val movies = response.results.map { it.toMovie() }
            emit(Resource.Success(movies))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An error occurred while fetching trending movies"))
        }
    }

    override fun getNowPlayingMovies(
        language: String,
        page: Int,
        region: String?,
    ): Flow<Resource<List<Movie>>> = flow {
        try {
            val response = movieService.getNowPlayingMovies(language, page, region)
            val movies = response.results.map { it.toMovie() }
            emit(Resource.Success(movies))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An error occurred while fetching now playing movies"))
        }
    }

    override fun getPopularMovies(
        language: String,
        page: Int,
        region: String?,
    ): Flow<Resource<List<Movie>>> = flow {
        try {
            val response = movieService.getPopularMovies(language, page, region)
            val movies = response.results.map { it.toMovie() }
            emit(Resource.Success(movies))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An error occurred while fetching popular movies"))
        }
    }

    override fun getTopRatedMovies(
        language: String,
        page: Int,
        region: String?,
    ): Flow<Resource<List<Movie>>> = flow {
        try {
            val response = movieService.getTopRatedMovies(language, page, region)
            val movies = response.results.map { it.toMovie() }
            emit(Resource.Success(movies))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An error occurred while fetching top rated movies"))
        }
    }

    override fun getUpcomingMovies(
        language: String,
        page: Int,
        region: String?,
    ): Flow<Resource<List<Movie>>> = flow {
        try {
            val response = movieService.getUpcomingMovies(language, page, region)
            val movies = response.results.map { it.toMovie() }
            emit(Resource.Success(movies))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An error occurred while fetching upcoming movies"))
        }
    }

    override suspend fun getMovieDetails(
        movieId: Int,
        language: String,
        appendToResponse: String?,
    ): Resource<MovieDetails> {
        return try {
            val response = movieService.getMovieDetails(movieId, language, appendToResponse)
            Resource.Success(response.toMovieDetails())
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred while fetching movie details")
        }
    }

    override suspend fun getMovieCredits(
        movieId: Int,
        language: String,
    ): Resource<MovieCredits> {
        return try {
            val response = movieService.getMovieCredits(movieId, language)
            Resource.Success(response.toCredits())
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred while fetching movie credits")
        }
    }

    override suspend fun getMovieVideos(
        movieId: Int,
        language: String,
    ): Resource<MovieVideos> {
        return try {
            val response = movieService.getMovieVideos(movieId, language)
            Resource.Success(response.toMovieVideos())
        } catch (e : Exception) {
            Resource.Error(e.message ?: "An error occurred while fetching movie videos")
        }
    }

    override suspend fun getSimilarMovies(
        movieId: Int,
        language: String,
        page: Int,
    ): Resource<List<Movie>> {
        return try {
            val response = movieService.getSimilarMovies(movieId, language, page)
            val movies = response.results.map { it.toMovie() }
            Resource.Success(movies)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred while fetching similar movies")
        }
    }

}