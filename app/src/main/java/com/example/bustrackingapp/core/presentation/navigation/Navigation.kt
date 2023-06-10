package com.example.bustrackingapp.core.presentation.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.bustrackingapp.core.domain.models.BusRoute
import com.example.bustrackingapp.core.presentation.dashboard.DashboardScreen
import com.example.bustrackingapp.core.presentation.dashboard.SplashScreen
import com.example.bustrackingapp.feature_bus_routes.domain.models.BusRouteWithStops
import com.example.bustrackingapp.feature_bus_routes.presentation.route_details.RouteDetailsScreen
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

        authNavGraph(navController)

        composable(
            route = ScreenRoutes.DashboardScreen.route
        ){
            DashboardScreen()
        }

//        composable(
//            route = ScreenRoutes.RouteDetailsScreen.route,
//        ){
//            val routeId = it.arguments?.getString("routeNo")?:""
//            RouteDetailsScreen(routeId)
//        }

    }
}


sealed class ScreenRoutes(val route : String){
    object SplashScreen : ScreenRoutes("splash")
    object AuthScreen : ScreenRoutes("auth")
    object DashboardScreen : ScreenRoutes("dashboard")
    object BusRouteDetailsScreen : ScreenRoutes("busRoute/{routeNo}")
    object BusStopDetailsScreen : ScreenRoutes("busStop/{stopNo}")
}