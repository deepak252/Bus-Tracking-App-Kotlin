package com.example.bustrackingapp.core.presentation.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.bustrackingapp.core.presentation.dashboard.DashboardScreen
import com.example.bustrackingapp.core.presentation.dashboard.SplashScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation(
    navController : NavHostController = rememberAnimatedNavController(),
    startDestination : String =  ScreenRoutes.AuthScreen.route,
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = startDestination,
    ){
        composable(
            route = ScreenRoutes.SplashScreen.route
        ){
            SplashScreen(
                onSplashComplete = {
                    navController.popBackStack()
                    navController.navigate(ScreenRoutes.DashboardScreen.route)
                }
            )
        }
        composable(
            route = ScreenRoutes.DashboardScreen.route
        ){
            DashboardScreen()
        }
        authNavGraph(navController)

    }
}


sealed class ScreenRoutes(val route : String){
    object SplashScreen : ScreenRoutes("splash")
    object AuthScreen : ScreenRoutes("auth")
    object DashboardScreen : ScreenRoutes("dashboard")
}