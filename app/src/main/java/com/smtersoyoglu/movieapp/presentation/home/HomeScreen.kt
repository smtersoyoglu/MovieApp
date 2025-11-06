package com.smtersoyoglu.movieapp.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.smtersoyoglu.movieapp.R
import com.smtersoyoglu.movieapp.presentation.components.ErrorScreen
import com.smtersoyoglu.movieapp.presentation.components.LoadingBar
import com.smtersoyoglu.movieapp.presentation.components.NoInternetScreen
import com.smtersoyoglu.movieapp.presentation.home.components.FeaturedMovieCard
import com.smtersoyoglu.movieapp.presentation.home.components.MovieSection

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToMovieDetail: (Int) -> Unit,
    navigateToHome: () -> Unit = {},
    navigateToMovieList: (String, String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val nowPlayingPagingItems = viewModel.nowPlayingMoviesPaging.collectAsLazyPagingItems()
    val popularPagingItems = viewModel.popularMoviesPaging.collectAsLazyPagingItems()
    val topRatedPagingItems = viewModel.topRatedMoviesPaging.collectAsLazyPagingItems()
    val upcomingPagingItems = viewModel.upcomingMoviesPaging.collectAsLazyPagingItems()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        when {
            uiState.isNoInternet -> {
                NoInternetScreen(
                    onRetry = { viewModel.retry() }
                )
            }

            uiState.isLoading -> {
                LoadingBar()
            }

            !uiState.error.isNullOrBlank() -> {
                ErrorScreen(
                    message = uiState.error ?: stringResource(R.string.error_message)
                )
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    item {
                        FeaturedMovieCard(
                            movies = uiState.trendingMovieList,
                            onMovieClicked = navigateToMovieDetail
                        )
                    }

                    item {
                        MovieSection(
                            title = stringResource(R.string.now_playing),
                            movies = nowPlayingPagingItems.itemSnapshotList.items.take(20),
                            onMovieClicked = navigateToMovieDetail,
                            onSeeMoreClicked = { navigateToMovieList("Now Playing", "now_playing") }
                        )
                    }

                    item {
                        MovieSection(
                            title = stringResource(R.string.popular),
                            movies = popularPagingItems.itemSnapshotList.items.take(20),
                            onMovieClicked = navigateToMovieDetail,
                            onSeeMoreClicked = { navigateToMovieList("Popular", "popular") }
                        )
                    }

                    item {
                        MovieSection(
                            title = stringResource(R.string.top_rated),
                            movies = topRatedPagingItems.itemSnapshotList.items.take(20),
                            onMovieClicked = navigateToMovieDetail,
                            onSeeMoreClicked = { navigateToMovieList("Top Rated", "top_rated") }
                        )
                    }

                    item {
                        MovieSection(
                            title = stringResource(R.string.upcoming),
                            movies = upcomingPagingItems.itemSnapshotList.items.take(20),
                            onMovieClicked = navigateToMovieDetail,
                            onSeeMoreClicked = { navigateToMovieList("Upcoming", "upcoming") }
                        )
                    }
                }
            }
        }
    }
}