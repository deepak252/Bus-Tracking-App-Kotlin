package com.example.bustrackingapp.core.presentation.dashboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SplashScreen(
//    splashViewModel: SplashViewModel = hiltViewModel(),
    onSplashComplete : ()->Unit
) {
//    LaunchedEffect(key1 = Unit){
//        delay(2000)
//        onSplashComplete()
////        navController.popBackStack(Screen.Splash.route,true)
//    }
    Scaffold(
    ) {paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.Center

        )  {
            Icon(
                Icons.Default.Home,
                contentDescription = "splash_icon",
                modifier = Modifier.size(50.dp)
            )
        }

    }
}