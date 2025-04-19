package com.smtersoyoglu.movieapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.smtersoyoglu.movieapp.navigation.Screen.Welcome
import com.smtersoyoglu.movieapp.navigation.Screen.Login
import com.smtersoyoglu.movieapp.navigation.Screen.Register
import com.smtersoyoglu.movieapp.navigation.Screen.Home
import com.smtersoyoglu.movieapp.navigation.Screen.Detail
import com.smtersoyoglu.movieapp.navigation.Screen.Search
import com.smtersoyoglu.movieapp.navigation.Screen.Favorite
import com.smtersoyoglu.movieapp.presentation.detail.DetailScreen
import com.smtersoyoglu.movieapp.presentation.favorite.FavoriteScreen
import com.smtersoyoglu.movieapp.presentation.home.HomeScreen
import com.smtersoyoglu.movieapp.presentation.login.LoginScreen
import com.smtersoyoglu.movieapp.presentation.person.PersonDetailScreen
import com.smtersoyoglu.movieapp.presentation.register.RegisterScreen
import com.smtersoyoglu.movieapp.presentation.search.SearchScreen
import com.smtersoyoglu.movieapp.presentation.welcome.WelcomeScreen

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

        composable<Welcome> {
            WelcomeScreen(
                onNavigateToSignIn = { navController.navigate(Login) },
                onNavigateToSignUp = { navController.navigate(Register) },
            )
        }

        composable<Login> {
            LoginScreen(
                onSignInSuccess = { navController.navigate(Home) },
                onNavigateToSignUp = { navController.navigate(Register) },
            )
        }

        composable<Register> {
            RegisterScreen(
                onSignUpSuccess = { navController.navigate(Login) },
                onNavigateToSignIn = { navController.popBackStack() }
            )
        }

        composable<Home> {
            HomeScreen(
                navController = navController
            )
        }

        composable<Detail> {
            DetailScreen(
                navController = navController,
                onBackClick = { navController.popBackStack() },
            )
        }

        composable<Screen.Person> {
            PersonDetailScreen(
                navController = navController,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable<Search> {
            SearchScreen(
                navController = navController
            )
        }

        composable<Favorite> {
            FavoriteScreen(
                navController = navController
            )
        }
    }
}