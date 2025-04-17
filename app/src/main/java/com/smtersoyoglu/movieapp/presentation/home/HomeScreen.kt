package com.smtersoyoglu.movieapp.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.smtersoyoglu.movieapp.R
import com.smtersoyoglu.movieapp.common.formatDate
import com.smtersoyoglu.movieapp.domain.model.Movie
import com.smtersoyoglu.movieapp.navigation.Screen
import com.smtersoyoglu.movieapp.presentation.components.EmptyScreen
import com.smtersoyoglu.movieapp.presentation.components.LoadingBar
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavController,
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        when {
            uiState.isLoading -> {
                LoadingBar()
            }

            !uiState.error.isNullOrBlank() -> {
                EmptyScreen(message = uiState.error ?: "Bilinmeyen bir hata oluştu")
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    item {
                        FeaturedMovieCard(
                            movies = uiState.trendingMovieList,
                            onMovieClicked = { movieId ->
                                navController.navigate(Screen.Detail(movieId))
                            }
                        )
                    }

                    item {
                        MovieSection(
                            title = "Now Playing",
                            movies = uiState.nowPlayingMovieList,
                            onMovieClicked = { movieId ->
                                navController.navigate(Screen.Detail(movieId))
                            }
                        )
                    }

                    item {
                        MovieSection(
                            title = "Popular",
                            movies = uiState.popularMovieList,
                            onMovieClicked = { movieId ->
                                navController.navigate(Screen.Detail(movieId))
                            }
                        )
                    }

                    item {
                        MovieSection(
                            title = "Top Rated",
                            movies = uiState.topRatedMovieList,
                            onMovieClicked = { movieId ->
                                navController.navigate(Screen.Detail(movieId))
                            }
                        )
                    }

                    item {
                        MovieSection(
                            title = "Upcoming",
                            movies = uiState.upcomingMovieList,
                            onMovieClicked = { movieId ->
                                navController.navigate(Screen.Detail(movieId))
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FeaturedMovieCard(
    movies: List<Movie>,
    modifier: Modifier = Modifier,
    onMovieClicked: (Int) -> Unit,
) {
    if (movies.isEmpty()) {
        EmptyScreen(message = "Trend filmler yüklenemedi.")
        return
    }

    val pagerState = rememberPagerState(pageCount = { movies.size })

    LaunchedEffect(pagerState) {
        while (true) {
            delay(5000)
            val nextPage = (pagerState.currentPage + 1) % movies.size
            pagerState.animateScrollToPage(nextPage)
        }
    }

    Box(modifier = modifier) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            val movie = movies[page]
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { onMovieClicked(movie.id) }
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("https://image.tmdb.org/t/p/original${movie.backdropPath ?: movie.posterPath}")
                        .crossfade(true)
                        .error(R.drawable.ic_error)
                        .build(),
                    contentDescription = movie.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(450.dp)
                )

                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter),
                    thickness = 0.5.dp,
                    color = Color(0xFF800000)
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.4f)
                                ),
                                startY = 0f,
                                endY = 600f
                            )
                        )
                )

                Text(
                    text = "Trending this week",
                    color = Color.White,
                    style = MaterialTheme.typography.labelLarge.copy(fontSize = 24.sp),
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(top = 36.dp, start = 16.dp)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomStart)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = movie.title,
                        color = Color.White,
                        style = MaterialTheme.typography.headlineMedium.copy(fontSize = 24.sp),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        movie.releaseDate?.let {
                            Text(
                                text = it.formatDate(),
                                color = Color.White.copy(alpha = 0.8f),
                                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp)
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Icon(
                            painter = painterResource(id = R.drawable.ic_rating),
                            contentDescription = "Rating",
                            tint = Color.Yellow,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = "%.1f".format(movie.voteAverage),
                            color = Color.White.copy(alpha = 0.8f),
                            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp)
                        )
                    }

                    Text(
                        text = movie.overview.take(100) + "...",
                        color = Color.White.copy(alpha = 0.7f),
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 16.sp),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(movies.size) { index ->
                val color =
                    if (pagerState.currentPage == index) Color.White else Color.Gray.copy(alpha = 0.5f)
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                )
            }
        }
    }
}

@Composable
fun MovieSection(
    title: String,
    movies: List<Movie>,
    onMovieClicked: (Int) -> Unit,
) {
    if (movies.isEmpty()) return

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge.copy(
                color = Color.White,
                fontSize = 20.sp
            ),
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(
            contentPadding = PaddingValues(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(movies) { movie ->
                MovieItem(
                    movie = movie,
                    onMovieClicked = onMovieClicked
                )
            }
        }
    }
}

@Composable
fun MovieItem(
    movie: Movie,
    onMovieClicked: (Int) -> Unit,
) {
    Card(
        modifier = Modifier
            .width(140.dp)
            .clickable { onMovieClicked(movie.id) },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black)
    ) {
        Column {
            movie.posterPath?.let { poster ->
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("https://image.tmdb.org/t/p/w500$poster")
                        .crossfade(true)
                        .error(R.drawable.ic_error)
                        .build(),
                    contentDescription = movie.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    error = painterResource(R.drawable.ic_image_not_found),
                    fallback = painterResource(R.drawable.ic_image_not_found)
                )
            }
            Text(
                text = movie.title,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.White,
                    fontSize = 16.sp
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            )

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth(),
                thickness = 0.5.dp,
                color = Color(0xFF800000)
            )
        }
    }
}
