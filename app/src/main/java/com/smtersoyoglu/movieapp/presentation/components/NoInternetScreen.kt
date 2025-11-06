package com.smtersoyoglu.movieapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.smtersoyoglu.movieapp.R

@Composable
fun NoInternetScreen(
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_no_internet),
                contentDescription = "No Internet",
                alignment = Alignment.Center,
                modifier = Modifier.size(400.dp),
            )

            Text(
                text = "No Internet Connection",
                style = MaterialTheme.typography.headlineMedium,
                color = Color(0xFFF29727),
                textAlign = TextAlign.Center
            )

            Text(
                text = "Please check your internet connection and try again.",
                style = MaterialTheme.typography.headlineSmall,
                color = Color(0xFFB8B8B8),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 32.dp)
            )

            Button(
                onClick = onRetry,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .height(48.dp)
                    .width(200.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF29727)
                )
            ) {
                Text(
                    text = "Try Again",
                    color = Color(0xFF0E0E0E),
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                )
            }

            TextButton(
                onClick = { /* Handle button click */ },
                modifier = Modifier.padding(top = 8.dp),
                colors = ButtonDefaults.textButtonColors(
                    contentColor = Color(0xFFB8B8B8)
                )
            ) {
                Text(
                    text = "Go Back Home",
                    color = Color(0xFFF29727),
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}