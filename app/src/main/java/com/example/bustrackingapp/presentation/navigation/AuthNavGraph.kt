package com.example.bustrackingapp.presentation.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.example.bustrackingapp.presentation.screens.auth.sign_in.SignInScreen
import com.example.bustrackingapp.presentation.screens.auth.sign_up.SignUpScreen
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.authNavGraph(navController : NavHostController) {
    navigation(
        startDestination = AuthRoutes.SignInScreen.route,
        route = ScreenRoutes.AuthScreen.route
    ){
        composable(route = AuthRoutes.SignInScreen.route){
            SignInScreen(
                onRegisterClick = {
                    navController.navigate(AuthRoutes.SignUpScreen.route){
                        popUpTo(0)
                    }
                }
            )
        }
        composable(route = AuthRoutes.SignUpScreen.route){
            SignUpScreen(
                onSignInClick = {
                    navController.navigate(AuthRoutes.SignInScreen.route){
                        popUpTo(0)
                    }
                }
            )
        }
    }
}


sealed class AuthRoutes(val route : String){
    object SignInScreen : BottomNavRoutes("signIn")
    object SignUpScreen : BottomNavRoutes("signUp")
}