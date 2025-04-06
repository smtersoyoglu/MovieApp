package com.smtersoyoglu.movieapp.presentation.person

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.smtersoyoglu.movieapp.R
import com.smtersoyoglu.movieapp.common.SocialMediaLinks
import com.smtersoyoglu.movieapp.common.formatDate
import com.smtersoyoglu.movieapp.domain.model.PersonDetails
import com.smtersoyoglu.movieapp.domain.model.PersonExternalIds
import com.smtersoyoglu.movieapp.domain.model.PersonImage
import com.smtersoyoglu.movieapp.domain.model.PersonMovieCast
import com.smtersoyoglu.movieapp.navigation.Screen
import com.smtersoyoglu.movieapp.presentation.components.EmptyScreen
import com.smtersoyoglu.movieapp.presentation.components.ExternalLinkIconButton
import com.smtersoyoglu.movieapp.presentation.components.LoadingBar

@Composable
fun PersonDetailScreen(
    viewModel: PersonDetailViewModel = hiltViewModel(),
    navController: NavController,
    onBackClick: () -> Unit,
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

            uiState.error != null -> {
                EmptyScreen(message = uiState.error ?: "Bilinmeyen bir hata oluştu")
            }

            else -> {
                uiState.personDetails?.let { personDetails ->
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        item {
                            PersonHeader(
                                person = personDetails,
                                externalIds = uiState.personExternalIds,
                            )
                        }
                        item {
                            PersonBiography(
                                biography = personDetails.biography,
                                isExpanded = uiState.isBiographyExpanded,
                                onToggleExpand = { viewModel.toggleBiography() }
                            )
                        }
                        item {
                            uiState.personImages.takeIf { it.isNotEmpty() }?.let { images ->
                                PersonImagesSection(images = images)
                            }
                        }
                        item {
                            uiState.personMovieCredits?.let { credits ->
                                PersonMoviesSection(
                                    title = "Movies Acted In",
                                    movies = credits.cast.filter { it.character?.isNotEmpty() == true },
                                    onMovieClick = { movieId ->
                                        navController.navigate(Screen.Detail(movieId))
                                    }
                                )
                            }
                        }
                      }
                }
            }
        }
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .padding(top = 46.dp, start = 16.dp)
                .align(Alignment.TopStart)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.8f))
        ) {
            Icon(
                modifier = Modifier.size(28.dp),
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun PersonHeader(
    person: PersonDetails,
    externalIds: PersonExternalIds?,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(450.dp)
    ) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/original${person.profilePath}",
            contentDescription = person.name,
            modifier = Modifier
                .fillMaxSize()
                .blur(5.dp),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.4f)),
                        startY = 0f,
                        endY = 700f
                    )
                )
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500${person.profilePath}",
                contentDescription = person.name,
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
                error  = painterResource(R.drawable.ic_no_image_person),
                fallback = painterResource(R.drawable.ic_no_image_person)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = person.name,
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Text(
                text = person.knownForDepartment,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White.copy(alpha = 0.7f),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                person.birthday?.let {
                    Text(
                        text = "Born: ${it.formatDate()}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.6f)
                    )
                }
                person.deathday?.let {
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Died: ${it.formatDate()}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.6f)
                    )
                }
            }
            person.placeOfBirth?.let {
                Text(
                    text = "From: $it",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.6f),
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Popularity: ${"%.1f".format(person.popularity)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.6f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Gender: ${when (person.gender) { 1 -> "Female"; 2 -> "Male"; else -> "Unknown" }}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.6f)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.Center
            ) {
                externalIds?.instagramId?.let { instagramId ->
                    ExternalLinkIconButton(
                        url = SocialMediaLinks.getInstagramUrl(instagramId),
                        iconPainter = painterResource(R.drawable.ic_instagram),
                        contentDescription = "Instagram"
                    )
                }
                externalIds?.twitterId?.let { twitterId ->
                    ExternalLinkIconButton(
                        url = SocialMediaLinks.getTwitterUrl(twitterId),
                        iconPainter = painterResource(R.drawable.ic_twitter),
                        contentDescription = "Twitter"
                    )
                }
                externalIds?.imdbId?.let { imdbId ->
                    ExternalLinkIconButton(
                        url = SocialMediaLinks.getImdbUrl(imdbId),
                        iconPainter = painterResource(R.drawable.ic_imdb),
                        contentDescription = "IMDb"
                    )
                }
            }
        }
    }
}

@Composable
fun PersonBiography(
    biography: String?,
    isExpanded: Boolean,
    onToggleExpand: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = "Biography",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = biography ?: "No biography available",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White,
            maxLines = if (isExpanded) Int.MAX_VALUE else 5,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.clickable { onToggleExpand() }
        )
        if (biography != null && biography.length > 200) {
            Text(
                text = if (isExpanded) "Show Less" else "Show More",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(top = 4.dp)
                    .clickable { onToggleExpand() }
            )
        }
    }
}

@Composable
fun PersonImagesSection(images: List<PersonImage>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = "Photos",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(images.sortedByDescending { it.voteAverage }) { image ->
                AsyncImage(
                    model = "https://image.tmdb.org/t/p/w500${image.filePath}",
                    contentDescription = "Person Image",
                    modifier = Modifier
                        .size(150.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Composable
fun PersonMoviesSection(
    title: String,
    movies: List<PersonMovieCast>,
    onMovieClick: (Int) -> Unit
) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            contentPadding = PaddingValues(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(movies) { movie ->
                PersonMovieItem(movie = movie, onClick = onMovieClick)
            }
        }
    }
}

@Composable
fun PersonMovieItem(
    movie: PersonMovieCast,
    onClick: (Int) -> Unit
) {
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
            error  = painterResource(R.drawable.ic_image_not_found),
            fallback = painterResource(R.drawable.ic_image_not_found)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = movie.title,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        movie.releaseDate?.let {
            Text(
                text = it.formatDate(),
                style = MaterialTheme.typography.bodySmall,
                color = Color.White.copy(alpha = 0.7f),
                maxLines = 1,
            )
        }
        movie.character?.let {
            Text(
                text = "as $it",
                style = MaterialTheme.typography.bodySmall,
                color = Color.White.copy(alpha = 0.7f),
                maxLines = 1,
            )
        }
    }
}