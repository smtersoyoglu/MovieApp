package com.smtersoyoglu.movieapp.navigation.bottomnav

import com.smtersoyoglu.movieapp.navigation.Screen

data class BottomNavItem(
    val route: Screen,
    val title: String,
    val icon: Int,
)