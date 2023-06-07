package com.example.bustrackingapp.feature_bus_routes.presentation.route_details

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bustrackingapp.feature_bus_routes.presentation.bus_routes.BusRoutesViewModel
import com.example.bustrackingapp.ui.theme.Red400
import com.example.bustrackingapp.ui.theme.White


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusRouteDetailsScreen(
    busRoutesViewModel : BusRoutesViewModel = hiltViewModel(),
    snackbarState : SnackbarHostState = remember {
        SnackbarHostState()
    }
){

    LaunchedEffect(key1 = busRoutesViewModel.uiState.error){
        Log.d("BTLogger","showSnackbar")
        if(busRoutesViewModel.uiState.error!=null){
            snackbarState.showSnackbar(busRoutesViewModel.uiState.error!!)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Bus Routes",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarState,
            ){
                if(busRoutesViewModel.uiState.error!=null){
                    Snackbar(
                        snackbarData = it,
                        containerColor = Red400,
                        contentColor = White,

                        )
                }else{
                    Snackbar(snackbarData = it)
                }
            }
        },

        ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {

        }
    }
}

@Composable
fun BusRouteDetailsContainer(

){

}