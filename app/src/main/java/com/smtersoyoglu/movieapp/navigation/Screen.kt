package com.smtersoyoglu.movieapp.navigation

import kotlinx.serialization.Serializable

sealed interface Screen {

    @Serializable
    data object Welcome : Screen

    @Serializable
    data object Login : Screen

    @Serializable
    data object Register : Screen

    @Serializable
    data object Home : Screen

    @Serializable
    data class Detail(val movieId: Int) : Screen

    @Serializable
    data class Trailer(val videoKey: String) : Screen

    @Serializable
    data class Person(val personId: Int) : Screen

    @Serializable
    data object Search : Screen

    @Serializable
    data object Favorite : Screen

    @Serializable
    data object Profile : Screen

    companion object {
        fun getRoute(screen: Screen): String {
            return screen::class.qualifiedName.orEmpty()
        }

        fun shouldShowBottomBar(currentRoute: String?): Boolean {
            return when (currentRoute) {
                getRoute(Home), getRoute(Search), getRoute(Favorite) , getRoute(Profile) -> true

                else -> false
            }
        }
    }
}
