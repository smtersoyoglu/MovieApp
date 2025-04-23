package com.smtersoyoglu.movieapp.presentation.person

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
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
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.smtersoyoglu.movieapp.R
import com.smtersoyoglu.movieapp.common.SocialMediaLinks
import com.smtersoyoglu.movieapp.common.formatDate
import com.smtersoyoglu.movieapp.domain.model.PersonDetails
import com.smtersoyoglu.movieapp.domain.model.PersonExternalIds
import com.smtersoyoglu.movieapp.domain.model.PersonImage
import com.smtersoyoglu.movieapp.domain.model.PersonMovieCast
import com.smtersoyoglu.movieapp.presentation.components.ErrorScreen
import com.smtersoyoglu.movieapp.presentation.components.ExternalLinkIconButton
import com.smtersoyoglu.movieapp.presentation.components.LoadingBar
import com.smtersoyoglu.movieapp.presentation.components.PersonImageDialog
import com.smtersoyoglu.movieapp.presentation.theme.HorizontalDividerColor

@Composable
fun PersonDetailScreen(
    viewModel: PersonDetailViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    navigateToMovieDetail: (Int) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedPersonImageUrl by remember { mutableStateOf<String?>(null) }

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
                                PersonImagesSection(
                                    images = images,
                                    onImageClick = { imageUrl ->
                                        selectedPersonImageUrl = imageUrl
                                    })
                            }
                        }
                        item {
                            uiState.personMovieCredits?.let { credits ->
                                PersonMoviesSection(
                                    title = stringResource(R.string.movies_acted_in),
                                    movies = credits.cast.filter { it.character?.isNotEmpty() == true },
                                    onMovieClick = navigateToMovieDetail
                                )
                            }
                        }
                    }
                    selectedPersonImageUrl?.let { url ->
                        PersonImageDialog(
                            imageUrl = url,
                            onDismiss = { selectedPersonImageUrl = null })
                    }
                }
            }
        }
        IconButton(
            onClick = navigateBack,
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
    modifier: Modifier = Modifier,
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

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            thickness = 0.5.dp,
            color = HorizontalDividerColor
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
                    .border(1.dp, HorizontalDividerColor, CircleShape)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
                error = painterResource(R.drawable.ic_no_image_person),
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
                        text = stringResource(R.string.born, it.formatDate()),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.6f)
                    )
                }
                person.deathday?.let {
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(R.string.died, it.formatDate()),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.6f)
                    )
                }
            }
            person.placeOfBirth?.let {
                Text(
                    text = stringResource(R.string.from, it),
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
                    text = stringResource(R.string.popularity, "%.1f".format(person.popularity)),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.6f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(
                        R.string.gender, when (person.gender) {
                            1 -> stringResource(R.string.female); 2 -> stringResource(R.string.male);else -> stringResource(R.string.unknown)
                        }
                    ),
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
    onToggleExpand: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = stringResource(R.string.biography),
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = biography ?: stringResource(R.string.no_biography_available),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White,
            maxLines = if (isExpanded) Int.MAX_VALUE else 5,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.clickable { onToggleExpand() }
        )
        if (biography != null && biography.length > 200) {
            Text(
                text = if (isExpanded) stringResource(R.string.show_less) else stringResource(R.string.show_more),
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFFDC143C),
                modifier = Modifier
                    .padding(top = 4.dp)
                    .clickable { onToggleExpand() }
            )
        }

    }
    HorizontalDivider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp),
        thickness = 0.5.dp,
        color = HorizontalDividerColor
    )

}

@Composable
fun PersonImagesSection(
    images: List<PersonImage>,
    onImageClick: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = stringResource(R.string.photos),
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(images.sortedByDescending { it.voteAverage }) { image ->
                Column(
                    modifier = Modifier.width(150.dp)
                ) {
                    AsyncImage(
                        model = "https://image.tmdb.org/t/p/w500${image.filePath}",
                        contentDescription = "Person Image",
                        modifier = Modifier
                            .size(150.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .border(
                                1.dp,
                                Color(0xFFFFC107).copy(alpha = 0.2f),
                                RoundedCornerShape(8.dp)
                            )
                            .clickable { onImageClick(image.filePath) },
                        contentScale = ContentScale.Crop,
                        error = painterResource(R.drawable.ic_no_image_person),
                        fallback = painterResource(R.drawable.ic_no_image_person)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 4.dp),
                        thickness = 1.dp,
                        color = HorizontalDividerColor
                    )
                }
            }
        }
    }
}

@Composable
fun PersonMoviesSection(
    title: String,
    movies: List<PersonMovieCast>,
    onMovieClick: (Int) -> Unit,
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
                PersonMovieItem(movie = movie, onMovieClick = onMovieClick)
            }
        }
    }
}

@Composable
fun PersonMovieItem(
    movie: PersonMovieCast,
    onMovieClick: (Int) -> Unit,
) {
    Column(
        modifier = Modifier
            .width(140.dp)
            .clickable { onMovieClick(movie.id) }
    ) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
            contentDescription = movie.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(12.dp))
                .border(
                    1.dp,
                    Color(0xFFFFC107).copy(alpha = 0.2f),
                    RoundedCornerShape(12.dp)
                ),
            contentScale = ContentScale.Crop,
            error = painterResource(R.drawable.ic_image_not_found),
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