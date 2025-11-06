package com.smtersoyoglu.movieapp.presentation.detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.smtersoyoglu.movieapp.R
import com.smtersoyoglu.movieapp.domain.model.movie.MovieWatchProviders
import com.smtersoyoglu.movieapp.domain.model.movie.WatchProvider

@Composable
fun MovieWatchProviders(
    watchProviders: MovieWatchProviders?,
    modifier: Modifier = Modifier
) {
    if (watchProviders == null) return
    val countryCode = "TR"
    val countryProviders = watchProviders.results[countryCode]
        ?: watchProviders.results["US"]
        ?: watchProviders.results.values.firstOrNull()

    if (countryProviders == null) return

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = stringResource(id = R.string.where_to_watch),
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        countryProviders.flatrate?.let { providers ->
            if (providers.isNotEmpty()) {
                WatchProviderSection(
                    title = stringResource(id = R.string.subscription),
                    providers = providers,
                    backgroundColor = Color(0xFF1E88E5)
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        countryProviders.rent?.let { providers ->
            if (providers.isNotEmpty()) {
                WatchProviderSection(
                    title = stringResource(id = R.string.rent),
                    providers = providers,
                    backgroundColor = Color(0xFFFF9800)
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        countryProviders.buy?.let { providers ->
            if (providers.isNotEmpty()) {
                WatchProviderSection(
                    title = stringResource(id = R.string.buy),
                    providers = providers,
                    backgroundColor = Color(0xFF4CAF50)
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        countryProviders.free?.let { providers ->
            if (providers.isNotEmpty()) {
                WatchProviderSection(
                    title = stringResource(id = R.string.free),
                    providers = providers,
                    backgroundColor = Color(0xFF9C27B0)
                )
            }
        }
    }
}

@Composable
private fun WatchProviderSection(
    title: String,
    providers: List<WatchProvider>,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = backgroundColor,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(providers.take(6)) { provider ->
                WatchProviderItem(
                    provider = provider,
                    backgroundColor = backgroundColor
                )
            }
        }
    }
}

@Composable
private fun WatchProviderItem(
    provider: WatchProvider,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .size(85.dp)
            .border(
                1.dp,
                Color(0xFFFFC107).copy(alpha = 0.2f),
                RoundedCornerShape(12.dp)
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1A1A1A)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                if (provider.logoPath != null) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("https://image.tmdb.org/t/p/w92${provider.logoPath}")
                            .crossfade(true)
                            .build(),
                        contentDescription = provider.providerName,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(30.dp)
                    )
                } else {
                    Text(
                        text = provider.providerName.take(2).uppercase(),
                        color = Color.Black,
                        style = MaterialTheme.typography.labelLarge,
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = provider.providerName,
                color = Color.White,
                style = MaterialTheme.typography.labelMedium,
                textAlign = TextAlign.Center,
                maxLines = 2,
                lineHeight = 10.sp
            )
        }
    }
}