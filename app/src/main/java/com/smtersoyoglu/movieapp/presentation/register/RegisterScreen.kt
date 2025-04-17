package com.smtersoyoglu.movieapp.presentation.register

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.smtersoyoglu.movieapp.R
import com.smtersoyoglu.movieapp.presentation.components.LoadingBar

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    onSignUpSuccess: () -> Unit,
    onNavigateToSignIn: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.error) {
        uiState.error?.let { snackbarHostState.showSnackbar(it) }
    }
    LaunchedEffect(uiState.isRegistered) {
        if (uiState.isRegistered) onSignUpSuccess()
    }

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
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(66.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                contentAlignment = Alignment.TopCenter
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_movie_register),
                    contentDescription = "Movies Logo",
                    modifier = Modifier
                        .zIndex(1f)
                )
                Card(
                    modifier = Modifier
                        .padding(top = 205.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1C2526).copy(alpha = 0.9f))
                ) {
                    Column(
                        modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 48.dp, bottom = 24.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        var name by remember { mutableStateOf("") }
                        var email by remember { mutableStateOf("") }
                        var password by remember { mutableStateOf("") }
                        var confirmPassword by remember { mutableStateOf("") }

                        OutlinedTextField(
                            value = name,
                            onValueChange = { name = it },
                            label = { Text("Full Name") },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Person,
                                    contentDescription = "Name Icon",
                                    tint = Color(0xFFFFC107)
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
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
                            isError = uiState.error?.contains("email") == true,
                            supportingText = {
                                if (uiState.error?.contains("email") == true) {
                                    Text("Invalid email format", color = Color.Red)
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
                            isError = uiState.error?.contains("password") == true,
                            supportingText = {
                                if (uiState.error?.contains("password") == true) {
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

                        OutlinedTextField(
                            value = confirmPassword,
                            onValueChange = { confirmPassword = it },
                            label = { Text("Confirm Password") },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Lock,
                                    contentDescription = "Confirm Password Icon",
                                    tint = Color(0xFFFFC107)
                                )
                            },
                            visualTransformation = PasswordVisualTransformation(),
                            modifier = Modifier.fillMaxWidth(),
                            isError = password != confirmPassword && confirmPassword.isNotEmpty(),
                            supportingText = {
                                if (password != confirmPassword && confirmPassword.isNotEmpty()) {
                                    Text("Passwords do not match", color = Color.Red)
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
                            onClick = { viewModel.register(email, password, confirmPassword) },
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
                                    text = "Sign Up",
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )
                            }
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
                    text = "Already have an account? ",
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 15.sp
                )
                TextButton(onClick = onNavigateToSignIn) {
                    Text(
                        text = "Sign In",
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