package com.smtersoyoglu.movieapp.navigation.bottomnav

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.smtersoyoglu.movieapp.R
import com.smtersoyoglu.movieapp.navigation.Screen

@Composable
fun BottomNavBar(navController: NavController) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route

    val bottomNavItems = listOf(
        BottomNavItem(
            route = Screen.Home,
            title = "Home",
            icon = R.drawable.ic_home
        ),
        BottomNavItem(
            route = Screen.Search,
            title = "Search",
            icon = R.drawable.ic_search
        ),
        BottomNavItem(
            route = Screen.Favorite,
            title = "Favorite",
            icon = R.drawable.ic_favorite
        )
    )

    NavigationBar(
        containerColor = Color.White,
        contentColor = Color.Black,
        modifier = Modifier
    ) {
        bottomNavItems.forEach { item ->
            val selected = currentDestination == Screen.getRoute(item.route)
            NavigationBarItem(
                icon = {
                    val scale by animateFloatAsState(if (selected) 1.2f else 1.0f)
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.title,
                        modifier = Modifier.scale(scale)
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp
                    )
                },
                selected = selected,
                alwaysShowLabel = selected,
                onClick = {
                    if (!selected) {
                        navController.navigate(item.route) {
                            popUpTo(item.route) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Black,
                    selectedTextColor = Color.Black,
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = Color(0xFFBBDEFB)
                )
            )
        }
    }
}


@Preview
@Composable
fun BottomNavBarPreview() {
    val navController = NavController(LocalContext.current)
    BottomNavBar(
        navController = navController
    )
}