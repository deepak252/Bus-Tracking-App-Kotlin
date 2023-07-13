package com.example.bustrackingapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bustrackingapp.core.presentation.components.LocationPermissionScreen
import com.example.bustrackingapp.core.presentation.navigation.Navigation
import com.example.bustrackingapp.core.presentation.navigation.ScreenRoutes
import com.example.bustrackingapp.core.util.DateTimeUtil
import com.example.bustrackingapp.core.util.LoggerUtil
import com.example.bustrackingapp.ui.theme.BusTrackingAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val logger = LoggerUtil(c="MainActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BusTrackingAppTheme(darkTheme = true) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val mainViewModel = viewModel<MainViewModel>()
                    val userType by mainViewModel.userType.collectAsState()
                    val token by mainViewModel.token.collectAsState()
                    val isLoading by mainViewModel.loading.collectAsState()

                    var initialScreen = ScreenRoutes.SplashScreen.route
                    if(!isLoading){
                        initialScreen = if( token.isEmpty()) ScreenRoutes.AuthScreen.route
                            else "dashboard"
//                        initialScreen = if( token.isEmpty()) ScreenRoutes.AuthScreen.route
//                        else ScreenRoutes.DashboardScreen.route
                    }

                    logger.info("Loading = $isLoading, InitialScreen = $initialScreen, Token = $token\"")

                    Navigation(
                        startDestination = initialScreen,
                        userType = userType
                    )
                }
            }
        }
    }
}
//
//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    BusTrackingAppTheme {
//        Greeting("Android")
//    }
//}