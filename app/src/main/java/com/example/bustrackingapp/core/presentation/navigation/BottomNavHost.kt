package com.example.bustrackingapp.core.presentation.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.bustrackingapp.core.util.LoggerUtil
import com.example.bustrackingapp.feature_home.presentation.home.HomeScreen
import com.example.bustrackingapp.feature_bus_routes.presentation.bus_routes.BusRoutesScreen
import com.example.bustrackingapp.feature_bus_routes.presentation.route_details.RouteDetailsScreen
import com.example.bustrackingapp.feature_bus_stop.presentation.stop_details.StopDetailsScreen
import com.example.bustrackingapp.feature_profile.presentation.profile.ProfileScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BottomNavHost(
    bottomNavController: NavHostController,
){
    val logger = LoggerUtil(c = "BottomNavHost")
    AnimatedNavHost(
        navController = bottomNavController,
        startDestination = BottomNavRoutes.HomeScreen.route
    ){
//        logger.info("Reload")
        composable(route = BottomNavRoutes.HomeScreen.route){
            HomeScreen(
                onBusStopClick = {
                    bottomNavController.navigate("busStop/${it.stopNo}")
                }
            )
        }
        composable(route = BottomNavRoutes.BusRoutesScreen.route){
            logger.info("Load BusRoutesScreen")
            BusRoutesScreen(
                onBusRouteClick = {
                    bottomNavController.navigate("busRoute/${it.routeNo}")
                }
            )
        }
        composable(route = BottomNavRoutes.ProfileScreen.route){
            ProfileScreen()
        }

        composable(
            route = ScreenRoutes.BusRouteDetailsScreen.route,
        ){
            val routeNo = it.arguments?.getString("routeNo")?:""
            RouteDetailsScreen(routeNo)
        }

        composable(
            route = ScreenRoutes.BusStopDetailsScreen.route,
        ){
            val stopNo = it.arguments?.getString("stopNo")?:""
            StopDetailsScreen(stopNo)
        }
    }
}


sealed class BottomNavRoutes(val route : String){
    object HomeScreen : BottomNavRoutes("home")
    object ProfileScreen : BottomNavRoutes("profile")
    object BusRoutesScreen : BottomNavRoutes("busRoutes")
}