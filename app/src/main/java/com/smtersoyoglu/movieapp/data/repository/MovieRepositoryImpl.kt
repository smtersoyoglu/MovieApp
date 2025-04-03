package com.smtersoyoglu.movieapp.data.repository

import com.smtersoyoglu.movieapp.common.Resource
import com.smtersoyoglu.movieapp.data.mapper.toCredits
import com.smtersoyoglu.movieapp.data.mapper.toGenre
import com.smtersoyoglu.movieapp.data.mapper.toGenreList
import com.smtersoyoglu.movieapp.data.mapper.toMovie
import com.smtersoyoglu.movieapp.data.mapper.toMovieDetails
import com.smtersoyoglu.movieapp.data.mapper.toMovieVideos
import com.smtersoyoglu.movieapp.data.mapper.toPersonDetails
import com.smtersoyoglu.movieapp.data.mapper.toPersonMovieCredits
import com.smtersoyoglu.movieapp.data.source.remote.MovieService
import com.smtersoyoglu.movieapp.domain.model.Genre
import com.smtersoyoglu.movieapp.domain.model.Movie
import com.smtersoyoglu.movieapp.domain.model.MovieCredits
import com.smtersoyoglu.movieapp.domain.model.MovieDetails
import com.smtersoyoglu.movieapp.domain.model.MovieVideos
import com.smtersoyoglu.movieapp.domain.model.PersonDetails
import com.smtersoyoglu.movieapp.domain.model.PersonMovieCredits
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
        } catch (e: Exception) {
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

    override suspend fun getPersonDetails(
        personId: Int,
        appendToResponse: String?,
        language: String,
    ): Resource<PersonDetails> {
        return try {
            val response = movieService.getPersonDetails(personId, appendToResponse, language)
            Resource.Success(response.toPersonDetails())
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred while fetching person details")
        }
    }

    override suspend fun getPersonMovieCredits(
        personId: Int,
        language: String,
    ): Resource<PersonMovieCredits> {
        return try {
            val response = movieService.getPersonMovieCredits(personId, language)
            Resource.Success(response.toPersonMovieCredits())
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred while fetching person movie credits")
        }
    }

    override suspend fun getSearchMovies(
        query: String,
        page: Int,
        includeAdult: Boolean,
        language: String,
    ): Resource<List<Movie>> {
        return try {
            val response = movieService.searchMovies(query, page, includeAdult, language)
            val movies = response.results.map { it.toMovie() }
            Resource.Success(movies)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred while searching movies")
        }
    }

    override suspend fun getGenres(language: String): Resource<List<Genre>> {
        return try {
            val response = movieService.getGenres(language)
            Resource.Success(response.toGenreList())
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred while fetching genres")
        }

    }

    override suspend fun getMoviesByGenre(
        genreId: Int,
        page: Int,
        language: String,
    ): Resource<List<Movie>> {
        return try {
            val response = movieService.getMoviesByGenre(genreId, page, language)
            val movies = response.results.map { it.toMovie() }
            Resource.Success(movies)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred while fetching movies by genre")
        }
    }

}