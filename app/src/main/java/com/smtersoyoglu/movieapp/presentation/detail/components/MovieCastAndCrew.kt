package com.smtersoyoglu.movieapp.presentation.detail.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smtersoyoglu.movieapp.R
import com.smtersoyoglu.movieapp.domain.model.movie.MovieCredits

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
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.cast),
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 18.sp),
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