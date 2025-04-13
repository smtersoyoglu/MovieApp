package com.smtersoyoglu.movieapp.di

import com.smtersoyoglu.movieapp.data.repository.AuthRepositoryImpl
import com.smtersoyoglu.movieapp.data.repository.FavoriteRepositoryImpl
import com.smtersoyoglu.movieapp.data.repository.MovieRepositoryImpl
import com.smtersoyoglu.movieapp.domain.repository.AuthRepository
import com.smtersoyoglu.movieapp.domain.repository.FavoriteRepository
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

    @Binds
    @Singleton
    abstract fun bindFavoriteRepository(repositoryImpl: FavoriteRepositoryImpl): FavoriteRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(repositoryImpl: AuthRepositoryImpl): AuthRepository

}