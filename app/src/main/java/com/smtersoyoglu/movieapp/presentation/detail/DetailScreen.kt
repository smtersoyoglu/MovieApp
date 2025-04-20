package com.smtersoyoglu.movieapp.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.smtersoyoglu.movieapp.R
import com.smtersoyoglu.movieapp.data.mapper.toGenreString
import com.smtersoyoglu.movieapp.domain.model.FavoriteMovie
import com.smtersoyoglu.movieapp.domain.model.Movie
import com.smtersoyoglu.movieapp.domain.model.MovieCredits
import com.smtersoyoglu.movieapp.domain.model.MovieDetails
import com.smtersoyoglu.movieapp.domain.model.MovieImage
import com.smtersoyoglu.movieapp.domain.model.MovieVideo
import com.smtersoyoglu.movieapp.navigation.Screen
import com.smtersoyoglu.movieapp.presentation.components.ErrorScreen
import com.smtersoyoglu.movieapp.presentation.components.ImageDialog
import com.smtersoyoglu.movieapp.presentation.components.LoadingBar
import com.smtersoyoglu.movieapp.presentation.theme.HorizontalDividerColor
import com.smtersoyoglu.movieapp.presentation.theme.TabRowColor
import kotlinx.coroutines.delay

@Composable
fun DetailScreen(
    viewModel: DetailViewModel = hiltViewModel(),
    navController: NavController,
    onBackClick: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    var selectedImageUrl by remember { mutableStateOf<String?>(null) }


    LaunchedEffect(uiState.message) {
        uiState.message?.let { message ->
            snackbarHostState.showSnackbar(message = message)
            delay(2000)
            viewModel.clearMessage()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        when {
            uiState.isLoading -> {
                LoadingBar()
            }

            uiState.error != null -> {
                ErrorScreen(message = uiState.error ?: stringResource(R.string.error_message))
            }

            else -> {
                uiState.movieDetails?.let { movieDetails ->
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        item {
                            MovieHeader(
                                movieDetails = movieDetails,
                                videos = uiState.movieVideos?.videos,
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
                                    onClick = { viewModel.selectTab(0) },
                                    text = {
                                        Text(
                                            text = stringResource(R.string.overview),
                                            color = if (uiState.selectedTab == 0) TabRowColor else Color.White
                                        )
                                    }
                                )
                                Tab(
                                    selected = uiState.selectedTab == 1,
                                    onClick = { viewModel.selectTab(1) },
                                    text = {
                                        Text(
                                            text = stringResource(R.string.images),
                                            color = if (uiState.selectedTab == 1) TabRowColor else Color.White
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
                                    onImageClick = { imageUrl ->
                                        selectedImageUrl = imageUrl
                                    }
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
                            uiState.movieCredits?.let { credits ->
                                MovieCastAndCrew(
                                    credits = credits,
                                    onCastClick = { personId ->
                                        navController.navigate(Screen.Person(personId))
                                    }
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
                                onMovieClick = { movieId ->
                                    navController.navigate(Screen.Detail(movieId))
                                }
                            )
                        }
                    }

                    IconButton(
                        onClick = {
                            val favoriteMovie = FavoriteMovie(
                                id = movieDetails.id,
                                title = movieDetails.title,
                                posterPath = movieDetails.posterPath,
                                overview = movieDetails.overview,
                                releaseDate = movieDetails.releaseDate,
                                runtime = movieDetails.runtime,
                                voteAverage = movieDetails.voteAverage,
                                genres = movieDetails.genres.toGenreString(),
                                addedDate = System.currentTimeMillis()
                            )
                            viewModel.toggleFavorite(favoriteMovie)
                        },
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
                        ImageDialog(imageUrl = url, onDismiss = { selectedImageUrl = null })
                    }
                }
            }
        }
        IconButton(
            onClick = onBackClick,
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MovieHeader(
    movieDetails: MovieDetails,
    videos: List<MovieVideo>?,
    modifier: Modifier = Modifier,
) {
    var showVideo by remember { mutableStateOf(false) }
    val lifecycleOwner = LocalLifecycleOwner.current

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(450.dp)
    ) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/original${movieDetails.backdropPath}",
            contentDescription = "Movie Backdrop",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop,
            error = painterResource(R.drawable.ic_image_not_found),
            fallback = painterResource(R.drawable.ic_image_not_found)
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.4f)),
                        startY = 0f,
                        endY = 450f
                    )
                )
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = movieDetails.title,
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            if (!movieDetails.tagline.isNullOrBlank()) {
                Text(
                    text = movieDetails.tagline,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center
                )
            }
            Row(
                modifier = Modifier.padding(top = 10.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                val year =
                    movieDetails.releaseDate?.takeIf { it.length >= 4 }?.substring(0, 4) ?: "N/A"
                Icon(
                    painter = painterResource(R.drawable.ic_calendar),
                    contentDescription = "Release Date",
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = year,
                    color = Color.White.copy(alpha = 0.7f),
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.width(12.dp))
                Icon(
                    painter = painterResource(R.drawable.ic_time),
                    contentDescription = "Runtime",
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = movieDetails.runtime?.let { "${it / 60}h ${it % 60}m" } ?: "N/A",
                    color = Color.White.copy(alpha = 0.7f),
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.width(12.dp))
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Rating",
                    tint = Color.Yellow,
                    modifier = Modifier.size(18.dp)

                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = "%.1f".format(movieDetails.voteAverage),
                    color = Color.White.copy(alpha = 0.7f),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            FlowRow(
                modifier = Modifier.padding(top = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                movieDetails.genres.forEach { genre ->
                    GenreChip(genre.name)
                }
            }
            videos?.find { it.type == "Trailer" && it.site == "YouTube" }?.let {
                IconButton(
                    onClick = { showVideo = true },
                    modifier = Modifier
                        .padding(top = 4.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_play),
                        contentDescription = "Play Trailer",
                        tint = Color.LightGray,
                    )
                }
            }
        }
        if (showVideo) {
            videos?.find { it.type == "Trailer" && it.site == "YouTube" }?.let { trailer ->
                AndroidView(
                    factory = { ctx ->
                        YouTubePlayerView(ctx).apply {
                            lifecycleOwner.lifecycle.addObserver(this)
                            enableAutomaticInitialization = false
                            initialize(object : AbstractYouTubePlayerListener() {
                                override fun onReady(youTubePlayer: YouTubePlayer) {
                                    youTubePlayer.loadVideo(trailer.key, 0f)
                                }
                            })
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 140.dp)
                        .height(250.dp)
                )
            }
        }
    }
}

@Composable
fun GenreChip(genre: String) {
    Box(
        modifier = Modifier
            .border(
                width = 0.5.dp,
                color = HorizontalDividerColor,
                shape = RoundedCornerShape(16.dp)
            )
            .background(
                color = Color.White.copy(alpha = 0.2f),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Text(
            text = genre,
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun MovieOverview(overview: String?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 4.dp, top = 8.dp)

    ) {
        Text(
            text = stringResource(R.string.overview),
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = overview ?: stringResource(R.string.no_overview_available),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White,
        )
    }
}

@Composable
fun MovieImagesSection(
    images: List<MovieImage>?,
    onImageClick: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = stringResource(R.string.images),
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(6.dp))
        if (images.isNullOrEmpty()) {
            Text(
                text = stringResource(R.string.no_images_available),
                color = Color.White
            )
        } else {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(images) { image ->
                    AsyncImage(
                        model = "https://image.tmdb.org/t/p/w500${image.filePath}",
                        contentDescription = "Movie backdrop",
                        modifier = Modifier
                            .size(width = 200.dp, height = 120.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { onImageClick(image.filePath) },
                        error = painterResource(R.drawable.ic_image_not_found),
                        fallback = painterResource(R.drawable.ic_image_not_found)
                    )
                }
            }
        }
    }
}

@Composable
fun MovieCastAndCrew(
    credits: MovieCredits,
    onCastClick: (Int) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.cast),
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White
            )
            Text(
                text = stringResource(R.string.see_all),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.LightGray,
                modifier = Modifier.clickable { /* Navigate to full cast screen */ }
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        if (credits.cast.isEmpty()) {
            Text(
                text = stringResource(R.string.no_cast_information_available),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 16.dp)
            )
        } else {
            LazyRow {
                items(credits.cast.take(10)) { cast ->
                    CastItem(
                        name = cast.name,
                        character = cast.character,
                        profilePath = cast.profilePath,
                        personId = cast.id,
                        onCastClick = onCastClick
                    )
                }
            }
        }
    }
}

@Composable
fun CastItem(
    name: String,
    character: String,
    profilePath: String?,
    personId: Int,
    onCastClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(end = 8.dp)
            .clickable { onCastClick(personId) },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500$profilePath",
            contentDescription = name,
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop,
            error = painterResource(R.drawable.ic_no_image_person),
            fallback = painterResource(R.drawable.ic_no_image_person)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = name,
            style = MaterialTheme.typography.labelSmall,
            color = Color.White,
            maxLines = 1,
            textAlign = TextAlign.Center
        )
        Text(
            text = character,
            style = MaterialTheme.typography.labelSmall,
            maxLines = 1,
            color = Color.White.copy(alpha = 0.6f),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun SimilarMoviesSection(movies: List<Movie>, onMovieClick: (Int) -> Unit) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = stringResource(R.string.similar_movies),
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        if (movies.isEmpty()) {
            Text(
                text = stringResource(R.string.no_similar_movies_found),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp)
            )
        } else {
            LazyRow(
                contentPadding = PaddingValues(8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(movies) { movie ->
                    SimilarMovieItem(movie, onMovieClick)
                }
            }
        }
    }
}

@Composable
fun SimilarMovieItem(movie: Movie, onClick: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .width(140.dp)
            .clickable { onClick(movie.id) }
    ) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
            contentDescription = movie.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop,
            error = painterResource(R.drawable.ic_image_not_found),
            fallback = painterResource(R.drawable.ic_image_not_found)
        )

        Text(
            text = movie.title,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}