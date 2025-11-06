package com.smtersoyoglu.movieapp.presentation.detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.smtersoyoglu.movieapp.R

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

        if (overview.isNullOrEmpty()) {
            Text(
                text = stringResource(R.string.no_overview_available),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
            )
        } else {
            Text(
                text = overview,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
            )
        }
    }
} 