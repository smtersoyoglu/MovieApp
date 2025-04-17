package com.smtersoyoglu.movieapp.presentation.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.smtersoyoglu.movieapp.R
import com.smtersoyoglu.movieapp.presentation.components.LoadingBar

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onSignInSuccess: () -> Unit,
    onNavigateToSignUp: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.error) {
        uiState.error?.let { snackbarHostState.showSnackbar(it) }
    }
    LaunchedEffect(uiState.isLoggedIn) {
        if (uiState.isLoggedIn) onSignInSuccess()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFF800000), Color.Black),
                        startY = 0f,
                        endY = Float.POSITIVE_INFINITY
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_movie_login),
                contentDescription = "Movies Logo",
                tint = Color.Unspecified,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Popcorn’s Portal to the Cinematic Cosmos",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Grab your popcorn and step into the movie magic!",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White.copy(alpha = 0.7f)
            )
            Spacer(modifier = Modifier.height(28.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1C2526).copy(alpha = 0.9f))
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    var email by remember { mutableStateOf("") }
                    var password by remember { mutableStateOf("") }

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Email,
                                contentDescription = "Email Icon",
                                tint = Color(0xFFFFC107)
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        isError = uiState.error != null && email.isEmpty(),
                        supportingText = {
                            if (uiState.error != null && email.isEmpty()) {
                                Text("Invalid email", color = Color.Red)
                            }
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedLabelColor = Color(0xFFFFC107),
                            unfocusedLabelColor = Color.White.copy(alpha = 0.7f),
                            focusedBorderColor = Color(0xFFFFC107),
                            unfocusedBorderColor = Color.Gray
                        )
                    )

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Lock,
                                contentDescription = "Password Icon",
                                tint = Color(0xFFFFC107)
                            )
                        },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        isError = uiState.error != null && password.length < 6,
                        supportingText = {
                            if (uiState.error != null && password.length < 6) {
                                Text("Password must be at least 6 characters", color = Color.Red)
                            }
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedLabelColor = Color(0xFFFFC107),
                            unfocusedLabelColor = Color.White.copy(alpha = 0.7f),
                            focusedBorderColor = Color(0xFFFFC107),
                            unfocusedBorderColor = Color.Gray
                        )
                    )

                    Button(
                        onClick = { viewModel.signIn(email, password) },
                        enabled = !uiState.isLoading,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC107)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        if (uiState.isLoading) {
                            LoadingBar()
                        } else {
                            Text(
                                text = "Sign In",
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        }
                    }

                    OutlinedButton(
                        onClick = { /* Google ile giris sonradan eklenecek */ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        border = BorderStroke(1.dp, Color.White),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_google),
                                contentDescription = "Sign In with Google",
                                tint = Color.Unspecified,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Sign In with Google",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Don't have an account? ",
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 15.sp
                )
                TextButton(onClick = onNavigateToSignUp) {
                    Text(
                        text = "Sign Up",
                        color = Color(0xFFFFC107),
                        fontSize = 15.sp
                    )
                }
            }
        }
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}