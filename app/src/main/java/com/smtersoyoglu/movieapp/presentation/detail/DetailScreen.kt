package com.smtersoyoglu.movieapp.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.smtersoyoglu.movieapp.R
import com.smtersoyoglu.movieapp.data.mapper.favorite.toGenreString
import com.smtersoyoglu.movieapp.domain.model.favorite.FavoriteMovie
import com.smtersoyoglu.movieapp.presentation.components.ErrorScreen
import com.smtersoyoglu.movieapp.presentation.components.ImageDialog
import com.smtersoyoglu.movieapp.presentation.components.LoadingBar
import com.smtersoyoglu.movieapp.presentation.detail.components.MovieCastAndCrew
import com.smtersoyoglu.movieapp.presentation.detail.components.MovieHeader
import com.smtersoyoglu.movieapp.presentation.detail.components.MovieImagesSection
import com.smtersoyoglu.movieapp.presentation.detail.components.MovieOverview
import com.smtersoyoglu.movieapp.presentation.detail.components.MovieWatchProviders
import com.smtersoyoglu.movieapp.presentation.detail.components.SimilarMoviesSection
import com.smtersoyoglu.movieapp.presentation.theme.HorizontalDividerColor
import com.smtersoyoglu.movieapp.presentation.theme.TabRowColor
import kotlinx.coroutines.delay

@Composable
fun DetailScreen(
    viewModel: DetailViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    navigateToMovieDetail: (Int) -> Unit,
    navigateToPersonDetail: (Int) -> Unit,
    navigateToTrailer: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    var selectedImageUrl by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(uiState.message) {
        uiState.message?.let { message ->
            snackbarHostState.showSnackbar(message = message)
            delay(2000)
            viewModel.clearMessage()
        }
    }

    when {
        uiState.isLoading -> {
            LoadingBar()
        }
        uiState.error != null -> {
            ErrorScreen(message = uiState.error ?: "An error occurred")
        }
        else -> {
            DetailContent(
                uiState = uiState,
                onNavigateBack = navigateBack,
                onNavigateToMovieDetail = navigateToMovieDetail,
                onNavigateToPersonDetail = navigateToPersonDetail,
                onNavigateToTrailer = navigateToTrailer,
                onToggleFavorite = { viewModel.toggleFavorite() },
                onSelectTab = { tabIndex ->
                    viewModel.selectTab(tabIndex)
                },
                snackbarHostState = snackbarHostState,
                selectedImageUrl = selectedImageUrl,
                onImageClick = { imageUrl ->
                    selectedImageUrl = imageUrl
                },
                onDismissImage = {
                    selectedImageUrl = null
                }
            )
        }
    }
}

@Composable
fun DetailContent(
    uiState: DetailUiState,
    onNavigateBack: () -> Unit,
    onNavigateToMovieDetail: (Int) -> Unit,
    onNavigateToPersonDetail: (Int) -> Unit,
    onNavigateToTrailer: (String) -> Unit,
    onToggleFavorite: () -> Unit,
    onSelectTab: (Int) -> Unit,
    snackbarHostState: SnackbarHostState,
    selectedImageUrl: String?,
    onImageClick: (String) -> Unit,
    onDismissImage: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        uiState.movieDetails?.let { movieDetails ->
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    MovieHeader(
                        movieDetails = movieDetails,
                        videos = uiState.movieVideos?.videos,
                        navigateToTrailer = onNavigateToTrailer
                    )
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        thickness = 0.5.dp,
                        color = HorizontalDividerColor
                    )
                }

                item {
                    TabRow(
                        selectedTabIndex = uiState.selectedTab,
                        containerColor = Color.Transparent,
                        contentColor = Color.White,
                        indicator = { tabPositions ->
                            TabRowDefaults.SecondaryIndicator(
                                modifier = Modifier
                                    .tabIndicatorOffset(tabPositions[uiState.selectedTab])
                                    .height(1.5.dp),
                                color = TabRowColor
                            )
                        }
                    ) {
                        Tab(
                            selected = uiState.selectedTab == 0,
                            onClick = { onSelectTab(0) },
                            text = {
                                Text(
                                    text = stringResource(R.string.overview),
                                    color = if (uiState.selectedTab == 0) TabRowColor else Color.White
                                )
                            }
                        )

                        Tab(
                            selected = uiState.selectedTab == 1,
                            onClick = { onSelectTab(1) },
                            text = {
                                Text(
                                    text = stringResource(R.string.images),
                                    color = if (uiState.selectedTab == 1) TabRowColor else Color.White
                                )
                            }
                        )

                        Tab(
                            selected = uiState.selectedTab == 2,
                            onClick = { onSelectTab(2) },
                            text = {
                                Text(
                                    text = stringResource(R.string.watch_providers),
                                    color = if (uiState.selectedTab == 2) TabRowColor else Color.White
                                )
                            }
                        )
                    }
                }

                item {
                    when (uiState.selectedTab) {
                        0 -> MovieOverview(overview = movieDetails.overview)
                        1 -> MovieImagesSection(
                            images = uiState.movieImages?.backdrops,
                            onImageClick = onImageClick
                        )
                        2 -> MovieWatchProviders(watchProviders = uiState.movieWatchProviders)
                    }
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        thickness = 0.5.dp,
                        color = HorizontalDividerColor
                    )
                }


                item {
                    uiState.movieCredits?.let { credits ->
                        MovieCastAndCrew(
                            credits = credits,
                            onCastClick = onNavigateToPersonDetail
                        )
                    }
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        thickness = 0.5.dp,
                        color = HorizontalDividerColor
                    )
                }

                item {
                    SimilarMoviesSection(
                        movies = uiState.similarMovies,
                        onMovieClick = onNavigateToMovieDetail
                    )
                }
            }

            IconButton(
                onClick = onToggleFavorite ,
                modifier = Modifier
                    .padding(top = 36.dp, end = 16.dp)
                    .align(Alignment.TopEnd)
                    .clip(CircleShape)
                    .size(46.dp)
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.8f))
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = if (uiState.isFavorite) painterResource(R.drawable.ic_not_favorite) else painterResource(
                        R.drawable.ic_favorite
                    ),
                    contentDescription = "Favorite",
                    tint = if (uiState.isFavorite) Color.Red else MaterialTheme.colorScheme.onSurface
                )
            }
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.align(Alignment.BottomCenter)
            )

            selectedImageUrl?.let { url ->
                ImageDialog(imageUrl = url, onDismiss = onDismissImage)
            }
        }

        IconButton(
            onClick = onNavigateBack,
            modifier = Modifier
                .padding(top = 36.dp, start = 16.dp)
                .align(Alignment.TopStart)
                .clip(CircleShape)
                .size(46.dp)
                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.8f))
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
