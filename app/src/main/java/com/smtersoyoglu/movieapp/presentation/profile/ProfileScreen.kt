package com.smtersoyoglu.movieapp.presentation.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.smtersoyoglu.movieapp.R
import com.smtersoyoglu.movieapp.presentation.components.LoadingBar
import com.smtersoyoglu.movieapp.presentation.theme.HorizontalDividerColor

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    onSignOut: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.error) {
        uiState.error?.let { snackbarHostState.showSnackbar(it) }
    }
    LaunchedEffect(uiState.isSignedOut) {
        if (uiState.isSignedOut) onSignOut()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        if (uiState.isLoading) {
            LoadingBar()
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(21.dp)
                    .offset(y = 80.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_profile_logo),
                    contentDescription = "Profile Image",
                    modifier = Modifier.size(270.dp)
                )

                Spacer(modifier = Modifier.height(28.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = HorizontalDividerColor,
                            shape = RoundedCornerShape(16.dp)
                        ),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1C2526).copy(alpha = 0.9f))
                ) {
                    Column(
                        modifier = Modifier.padding(32.dp),
                        verticalArrangement = Arrangement.spacedBy(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Full Name Icon",
                                tint = Color(0xFFFFC107)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text(
                                    text = stringResource(R.string.full_name),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.White.copy(alpha = 0.7f)
                                )
                                Text(
                                    text = uiState.userProfile?.fullName ?: "N/A",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = Color.White
                                )
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = "Email Icon",
                                tint = Color(0xFFFFC107)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text(
                                    text = stringResource(R.string.email),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.White.copy(alpha = 0.7f)
                                )
                                Text(
                                    text = uiState.userProfile?.email ?: "N/A",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = Color.White
                                )
                            }
                        }

                        HorizontalDivider(
                            thickness = 1.dp,
                            color = Color.White.copy(alpha = 0.3f)
                        )


                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { viewModel.onAboutClicked() },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = "About Icon",
                                tint = Color(0xFFFFC107)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = stringResource(R.string.about),
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.White
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { viewModel.onSignOutClicked() },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                                contentDescription = "Sign Out Icon",
                                tint = Color(0xFFFFC107)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = "Sign Out",
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.White
                            )
                        }

                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
            }

            if (uiState.showSignOutDialog) {
                AlertDialog(
                    modifier = Modifier.border(
                        1.dp,
                        Color(0xFFFFC107).copy(alpha = 0.2f),
                        RoundedCornerShape(16.dp)
                    ),
                    onDismissRequest = { viewModel.onSignOutDialogDismissed() },
                    title = {
                        Text(
                            stringResource(R.string.sign_out),
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White
                        )
                    },
                    text = {
                        Text(
                            stringResource(R.string.sign_out_confirmation),
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White
                        )
                    },
                    confirmButton = {
                        TextButton(onClick = { viewModel.onSignOutConfirmed() }) {
                            Text(
                                stringResource(R.string.yes),
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color(0xFFFFC107)
                            )
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { viewModel.onSignOutDialogDismissed() }) {
                            Text(
                                stringResource(R.string.no),
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color(0xFFFFC107)
                            )
                        }
                    },
                    containerColor = Color(0xFF1C2526),
                    shape = RoundedCornerShape(16.dp)
                )
            }

            if (uiState.showAboutDialog) {
                AlertDialog(
                    onDismissRequest = { viewModel.onAboutDialogDismissed() },
                    modifier = Modifier.border(
                        1.dp,
                        Color(0xFFFFC107).copy(alpha = 0.2f),
                        RoundedCornerShape(16.dp)
                    ),
                    title = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = "About Icon",
                                tint = Color(0xFFFFC107),
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = stringResource(R.string.about),
                                style = MaterialTheme.typography.titleLarge,
                                color = Color.White,
                                textAlign = TextAlign.Center
                            )
                        }
                    },
                    text = {
                        Text(
                            text = stringResource(R.string.about_text),
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    },
                    confirmButton = {
                        TextButton(onClick = { viewModel.onAboutDialogDismissed() }) {
                            Text(
                                text = stringResource(R.string.ok),
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color(0xFFFFC107)
                            )
                        }
                    },
                    dismissButton = null,
                    containerColor = Color(0xFF1C2526),
                    shape = RoundedCornerShape(16.dp)
                )
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

