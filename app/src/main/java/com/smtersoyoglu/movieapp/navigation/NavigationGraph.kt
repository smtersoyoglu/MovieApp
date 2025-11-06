package com.smtersoyoglu.movieapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.smtersoyoglu.movieapp.navigation.Screen.Welcome
import com.smtersoyoglu.movieapp.navigation.Screen.Login
import com.smtersoyoglu.movieapp.navigation.Screen.Register
import com.smtersoyoglu.movieapp.navigation.Screen.Home
import com.smtersoyoglu.movieapp.navigation.Screen.SeeAll
import com.smtersoyoglu.movieapp.navigation.Screen.Detail
import com.smtersoyoglu.movieapp.navigation.Screen.Trailer
import com.smtersoyoglu.movieapp.navigation.Screen.Person
import com.smtersoyoglu.movieapp.navigation.Screen.Search
import com.smtersoyoglu.movieapp.navigation.Screen.Favorite
import com.smtersoyoglu.movieapp.navigation.Screen.Profile
import com.smtersoyoglu.movieapp.presentation.components.TrailerScreen
import com.smtersoyoglu.movieapp.presentation.detail.DetailScreen
import com.smtersoyoglu.movieapp.presentation.favorite.FavoriteScreen
import com.smtersoyoglu.movieapp.presentation.home.HomeScreen
import com.smtersoyoglu.movieapp.presentation.login.LoginScreen
import com.smtersoyoglu.movieapp.presentation.person.PersonDetailScreen
import com.smtersoyoglu.movieapp.presentation.profile.ProfileScreen
import com.smtersoyoglu.movieapp.presentation.register.RegisterScreen
import com.smtersoyoglu.movieapp.presentation.search.SearchScreen
import com.smtersoyoglu.movieapp.presentation.seeall.SeeAllScreen
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
                onNavigateToHome = { navController.navigate(Home) },
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
                onNavigateToSignIn = { navController.navigate(Login) }
            )
        }

        composable<Home> {
            HomeScreen(
                navigateToMovieDetail = { movieId ->
                    navController.navigate(Detail(movieId))
                },
                navigateToMovieList = { title, type ->
                    navController.navigate(SeeAll(title, type))
                }
            )
        }

        composable<SeeAll> {
            SeeAllScreen(
                onBack = { navController.navigateUp() },
                navigateToDetail = { id -> navController.navigate(Detail(id)) }
            )
        }

        composable<Detail> {
            DetailScreen(
                navigateBack = { navController.navigateUp() },
                navigateToMovieDetail = { movieId ->
                    navController.navigate(Detail(movieId))
                },
                navigateToPersonDetail = { personId ->
                    navController.navigate(Person(personId))
                },
                navigateToTrailer = { videoKey ->
                    navController.navigate(Trailer(videoKey))
                }
            )
        }

        composable<Trailer> {
            val videoKey = it.toRoute<Trailer>().videoKey
            TrailerScreen(
                videoKey = videoKey,
                onBack = { navController.navigateUp() }
            )
        }

        composable<Person> {
            PersonDetailScreen(
                navigateBack = { navController.navigateUp() },
                navigateToMovieDetail = { movieId ->
                    navController.navigate(Detail(movieId))
                }
            )
        }

        composable<Search> {
            SearchScreen(
                navigateToDetail = { movieId ->
                    navController.navigate(Detail(movieId))
                }
            )
        }

        composable<Favorite> {
            FavoriteScreen(
                navigateToDetail = { favorite ->
                    navController.navigate(Detail(favorite))
                }
            )
        }

        composable<Profile> {
            ProfileScreen(
                onSignOut = { navController.navigate(Login) }
            )
        }
    }
}