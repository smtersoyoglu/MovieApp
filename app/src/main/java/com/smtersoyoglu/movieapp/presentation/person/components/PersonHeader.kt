package com.smtersoyoglu.movieapp.presentation.person.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import com.smtersoyoglu.movieapp.R
import coil.compose.AsyncImage
import com.smtersoyoglu.movieapp.common.SocialMediaLinks
import com.smtersoyoglu.movieapp.common.formatDate
import com.smtersoyoglu.movieapp.domain.model.person.PersonDetails
import com.smtersoyoglu.movieapp.domain.model.person.PersonExternalIds
import com.smtersoyoglu.movieapp.presentation.components.ExternalLinkIconButton
import com.smtersoyoglu.movieapp.presentation.theme.HorizontalDividerColor

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
