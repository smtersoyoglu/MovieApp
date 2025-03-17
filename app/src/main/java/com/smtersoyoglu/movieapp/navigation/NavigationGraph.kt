package com.smtersoyoglu.movieapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.smtersoyoglu.movieapp.presentation.detail.DetailScreen
import com.smtersoyoglu.movieapp.presentation.home.HomeScreen

@Composable
fun NavigationGraph(
    navController: NavHostController,
    startDestination: Screen,
    modifier: Modifier = Modifier,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        composable<Screen.Home> {
            HomeScreen()
        }
        composable<Screen.Detail> {
            DetailScreen()
        }
    }
}