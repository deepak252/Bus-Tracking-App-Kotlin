package com.example.bustrackingapp.core.presentation.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.bustrackingapp.feature_home.presentation.home.HomeScreen
import com.example.bustrackingapp.feature_bus_routes.presentation.bus_routes.BusRoutesScreen
import com.example.bustrackingapp.feature_profile.presentation.profile.ProfileScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BottomNavHost(
    bottomNavController: NavHostController,
    mainNavController : NavHostController
){
    AnimatedNavHost(
        navController = bottomNavController,
        startDestination = BottomNavRoutes.HomeScreen.route
    ){
        composable(route = BottomNavRoutes.HomeScreen.route){
            HomeScreen()
        }
        composable(route = BottomNavRoutes.BusRoutesScreen.route){
            BusRoutesScreen(
                onBusRouteClick = {
                    mainNavController.navigate("busRoute/${it.routeNo}")
                }
            )
        }
        composable(route = BottomNavRoutes.ProfileScreen.route){
            ProfileScreen()
        }
    }
}


sealed class BottomNavRoutes(val route : String){
    object HomeScreen : BottomNavRoutes("home")
    object ProfileScreen : BottomNavRoutes("profile")
    object BusRoutesScreen : BottomNavRoutes("busRoutes")
}