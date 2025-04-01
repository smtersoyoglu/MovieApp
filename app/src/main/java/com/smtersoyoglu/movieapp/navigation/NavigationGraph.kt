package com.smtersoyoglu.movieapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.smtersoyoglu.movieapp.presentation.detail.DetailScreen
import com.smtersoyoglu.movieapp.presentation.favorite.FavoriteScreen
import com.smtersoyoglu.movieapp.presentation.home.HomeScreen
import com.smtersoyoglu.movieapp.presentation.person.PersonDetailScreen
import com.smtersoyoglu.movieapp.presentation.search.SearchScreen

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
            HomeScreen(
                navController = navController
            )
        }

        composable<Screen.Detail> {
            DetailScreen(
                navController = navController,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable<Screen.Person> {
            PersonDetailScreen(
                navController = navController,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable<Screen.Search> {
            SearchScreen(
                navController = navController
            )
        }

        composable<Screen.Favorite> {
            FavoriteScreen()
        }
    }
}