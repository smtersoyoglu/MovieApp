package com.smtersoyoglu.movieapp.di

import com.smtersoyoglu.movieapp.data.repository.MovieRepositoryImpl
import com.smtersoyoglu.movieapp.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMovieRepository(repositoryImpl: MovieRepositoryImpl): MovieRepository
}