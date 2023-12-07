package com.example.bustrackingapp.feature_home.presentation.home_driver

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bustrackingapp.ui.theme.Blue500
import com.example.bustrackingapp.ui.theme.Red400
import com.example.bustrackingapp.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeDriverScreen(
    homeDriverViewModel: HomeDriverViewModel = hiltViewModel(),
    snackbarState : SnackbarHostState = remember {
        SnackbarHostState()
    },
    onAllBusStopsClick : ()->Unit
){
//    LaunchedEffect(
//        key1 = homeDriverViewModel.uiState.errorNearbyBuses,
//        key2 = homeViewModel.uiState.nearbyBusStops,
//        key3 = homeViewModel.uiState.errorLocation
//    ){
//        Log.d("BTLogger","showSnackbar")
//        if(homeViewModel.uiState.errorLocation!=null){
//            snackbarState.showSnackbar(homeViewModel.uiState.errorLocation!!)
//        }else if(homeViewModel.uiState.errorNearbyBuses!=null){
//            snackbarState.showSnackbar(homeViewModel.uiState.errorNearbyBuses!!)
//        }else if(homeViewModel.uiState.errorNearbyStops!=null){
//            snackbarState.showSnackbar(homeViewModel.uiState.errorNearbyStops!!)
//        }
//
//    }
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = {
//                    Text(
//                        "Bus Tracker",
//                        style = MaterialTheme.typography.headlineSmall
//                    )
//                }
//            )
//        },
//        snackbarHost = {
//            SnackbarHost(
//                hostState = snackbarState,
//            ){
//                if(homeViewModel.uiState.errorNearbyBuses!=null || homeViewModel.uiState.errorNearbyStops!=null){
//                    Snackbar(
//                        snackbarData = it,
//                        containerColor = Red400,
//                        contentColor = White,
//                    )
//                }else{
//                    Snackbar(snackbarData = it)
//                }
//            }
//        },
//
//        ) { paddingValues ->
//        Box(
//            modifier = Modifier
//                .padding(paddingValues)
//                .fillMaxSize()
//        ) {
//            Column {
//                Text(
//                    "Nearby Buses",
//                    style = MaterialTheme.typography.titleSmall,
//                    modifier = Modifier.padding(
//                        horizontal = 8.dp
//                    )
//                )
//
//                Spacer(
//                    modifier = Modifier.height(24.dp)
//                )
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 8.dp),
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Text(
//                        "Nearby Bus Stops",
//                        style = MaterialTheme.typography.titleSmall
//                    )
//
//                    Text(
//                        "All Bus Stops",
//                        style = MaterialTheme.typography.labelMedium,
//                        color = Blue500,
//                        modifier = Modifier
//                            .clickable {
//                                onAllBusStopsClick()
//                            }
//                            .padding(
//                                start = 4.dp, top = 4.dp, end = 4.dp, bottom = 2.dp,
//                            )
//
//                    )
//
//                }
//
//            }
//
//        }
//    }

}
