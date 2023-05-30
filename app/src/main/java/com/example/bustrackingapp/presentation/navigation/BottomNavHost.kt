package com.example.bustrackingapp.presentation.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.bustrackingapp.presentation.screens.dashboard.BusRoutesScreen
import com.example.bustrackingapp.presentation.screens.dashboard.HomeScreen
import com.example.bustrackingapp.presentation.screens.dashboard.ProfileScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BottomNavHost(navController: NavHostController){
    AnimatedNavHost(
        navController = navController,
        startDestination = BottomNavRoutes.HomeScreen.route
    ){
        composable(route = BottomNavRoutes.HomeScreen.route){
            HomeScreen()
        }
        composable(route = BottomNavRoutes.BusRoutesScreen.route){
            BusRoutesScreen()
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