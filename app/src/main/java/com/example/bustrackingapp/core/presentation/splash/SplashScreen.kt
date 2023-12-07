package com.example.bustrackingapp.core.presentation.dashboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.bustrackingapp.core.presentation.components.logo.LogoCircularDark

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SplashScreen(
) {

    Scaffold(
    ) {paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.Center

        )  {
            LogoCircularDark(
                radius = 250.dp
            )
        }

    }
}