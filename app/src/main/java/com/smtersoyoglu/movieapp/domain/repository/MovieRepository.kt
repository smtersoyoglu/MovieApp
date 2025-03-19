package com.smtersoyoglu.movieapp.domain.repository

import com.smtersoyoglu.movieapp.common.Resource
import com.smtersoyoglu.movieapp.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getNowPlayingMovies(language: String = "en-US", page: Int = 1, region: String? = null, ): Flow<Resource<List<Movie>>>

    fun getPopularMovies(language: String = "en-US", page: Int = 1, region: String? = null, ): Flow<Resource<List<Movie>>>

    fun getTopRatedMovies(language: String = "en-US", page: Int = 1, region: String? = null, ): Flow<Resource<List<Movie>>>

    fun getUpcomingMovies(language: String = "en-US", page: Int = 1, region: String? = null, ): Flow<Resource<List<Movie>>>

}