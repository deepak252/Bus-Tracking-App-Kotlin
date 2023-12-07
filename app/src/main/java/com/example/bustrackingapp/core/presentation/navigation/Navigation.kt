package com.example.bustrackingapp.core.presentation.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.bustrackingapp.core.presentation.dashboard.DashboardScreen
import com.example.bustrackingapp.core.presentation.dashboard.SplashScreen
import com.example.bustrackingapp.feature_bus.presentation.bus_details.BusDetailsScreen
import com.example.bustrackingapp.feature_bus_routes.presentation.route_details.RouteDetailsScreen
import com.example.bustrackingapp.feature_bus_stop.presentation.all_stops.BusStopsScreen
import com.example.bustrackingapp.feature_bus_stop.presentation.stop_details.StopDetailsScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation(
    navController : NavHostController = rememberAnimatedNavController(),
    startDestination : String =  ScreenRoutes.AuthScreen.route,
    userType : String,
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = startDestination,
    ){
        composable(
            route = ScreenRoutes.SplashScreen.route
        ){
            SplashScreen()
        }

//        composable(
//            route = ScreenRoutes.UserTypeSelectionScreen.route
//        ){
//            UserTypeSelectionScreen(
//                onSelectedPassenger = {
//
//                },
//                onSelectedDriver = {
//
//                }
//            )
//        }

        authNavGraph(navController)

        composable(
            route = ScreenRoutes.DashboardScreen.route
        ){
            DashboardScreen(
                onBusRouteClick = {
                    navController.navigate("busRoute/$it")
                },
                onBusClick = {
                    navController.navigate("bus/$it")
                },
                onBusStopClick = {
                    navController.navigate("busStop/$it")
                },
                onAllBusStopsClick = {
                    navController.navigate(ScreenRoutes.BusStopsScreen.route)
                },
                userType = userType
            )
        }

        composable(
            route = ScreenRoutes.BusStopsScreen.route,
        ){
            BusStopsScreen(
                onStopItemClick = {stopNo->
                    navController.navigate("busStop/$stopNo")
                },
            )
        }

        composable(
            route = ScreenRoutes.BusRouteDetailsScreen.route,
        ){
            val routeNo = it.arguments?.getString("routeNo")?:""
            RouteDetailsScreen(
                routeNo= routeNo,
            )
        }

        composable(
            route = ScreenRoutes.BusDetailsScreen.route,
        ){
            val vehNo = it.arguments?.getString("vehNo")?:""
            BusDetailsScreen(
                vehNo
            )
        }

        composable(
            route = ScreenRoutes.BusStopDetailsScreen.route,
        ){
            val stopNo = it.arguments?.getString("stopNo")?:""
            StopDetailsScreen(
                stopNo,
                onBusRouteClick = {routeNo->
                    navController.navigate("busRoute/$routeNo")
                },
            )
        }
    }
}


sealed class ScreenRoutes(val route : String){
    object SplashScreen : ScreenRoutes("splash")
//    object UserTypeSelectionScreen : ScreenRoutes("userTypeSelection")
    object AuthScreen : ScreenRoutes("auth")
    object DashboardScreen : ScreenRoutes("dashboard")
    object BusDetailsScreen : ScreenRoutes("bus/{vehNo}")
    object BusRouteDetailsScreen : ScreenRoutes("busRoute/{routeNo}")
    object BusStopDetailsScreen : ScreenRoutes("busStop/{stopNo}")
    object BusStopsScreen : ScreenRoutes("busStops")
}