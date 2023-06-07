package com.example.bustrackingapp.feature_home.presentation.home

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bustrackingapp.core.presentation.components.CustomLoadingIndicator
import com.example.bustrackingapp.feature_bus.domain.models.BusWithRoute
import com.example.bustrackingapp.feature_bus_stop.domain.model.BusStopWithRoutes
import com.example.bustrackingapp.feature_home.presentation.components.BusStopTile
import com.example.bustrackingapp.feature_home.presentation.components.BusTile
import com.example.bustrackingapp.ui.theme.NavyBlue300
import com.example.bustrackingapp.ui.theme.Orange500
import com.example.bustrackingapp.ui.theme.Red400
import com.example.bustrackingapp.ui.theme.White
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeViewModel : HomeViewModel = hiltViewModel(),
    snackbarState : SnackbarHostState = remember {
        SnackbarHostState()
    }
){

    LaunchedEffect(key1 = homeViewModel.uiState.errorNearbyBuses, key2 = homeViewModel.uiState.nearbyBusStops){
        Log.d("BTLogger","showSnackbar")
        if(homeViewModel.uiState.errorNearbyBuses!=null){
            snackbarState.showSnackbar(homeViewModel.uiState.errorNearbyBuses!!)
        }else if(homeViewModel.uiState.errorNearbyStops!=null){
            snackbarState.showSnackbar(homeViewModel.uiState.errorNearbyStops!!)
        }

    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Bus Tracker",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarState,
            ){
                if(homeViewModel.uiState.errorNearbyBuses!=null || homeViewModel.uiState.errorNearbyStops!=null){
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
                Column {
                    Text(
                        "Nearby Buses",
                        style = MaterialTheme.typography.titleSmall
                    )
                    NearbyBusesList(
                        modifier = Modifier.weight(1f),
                        buses = {homeViewModel.uiState.nearbyBuses},
                        isLoading = homeViewModel.uiState.isLoadingNearbyBuses,
//                        isLoading = {homeViewModel.uiState.isLoadingNearbyBuses},
                    )
                    Spacer(
                        modifier = Modifier.height(24.dp)
                    )
                    Text(
                        "Nearby Bus Stops",
                        style = MaterialTheme.typography.titleSmall
                    )
                    NearbyBusStopsList(
                        modifier = Modifier.weight(3f),
                        busStops = {homeViewModel.uiState.nearbyBusStops},
                        isLoading = homeViewModel.uiState.isLoadingNearbyStops,
//                        isLoading = {homeViewModel.uiState.isLoadingNearbyStops},
                        isRefreshing = homeViewModel.uiState.isRefreshingNearbyStops,
//                        isRefreshing = {homeViewModel.uiState.isRefreshingNearbyStops},
                        onRefresh = homeViewModel::getNearbyStops
                    )
                }

            }
    }

}

@Composable
fun NearbyBusesList(
    modifier: Modifier = Modifier,
    buses : ()-> List<BusWithRoute>,
    isLoading :Boolean,
//    isLoading : ()->Boolean,
){
    if(isLoading){
        return CustomLoadingIndicator(
            modifier = modifier
        )
    }
    LazyRow(
        content = {
            items(buses()){
                BusTile(
                    routeNo = it.route.routeNo,
                    routeName = it.route.name,
                    vehNo = it.vehNo,
                    onClick = {}
                )
                Spacer(modifier = Modifier.width(14.dp))
            }
        },
        contentPadding = PaddingValues(8.dp),
    )
}

@Composable
fun NearbyBusStopsList(
    modifier: Modifier = Modifier,
    busStops : ()-> List<BusStopWithRoutes>,
    isLoading : Boolean,
//    isLoading : ()->Boolean,
    isRefreshing : Boolean,
//    isRefreshing : ()->Boolean,
    onRefresh : (isLoading : Boolean, isRefreshing : Boolean)->Unit
){
    if(isLoading){
        return CustomLoadingIndicator(
            modifier = modifier,
        )
    }
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
        onRefresh = { onRefresh(false, true) },
    ) {
        LazyColumn(
            content = {
                items(busStops()){
                    BusStopTile(
                        stopNo = it.stopNo,
                        stopName = it.name,
                        onClick = {}
                    )
                    Divider(
                        color = NavyBlue300,
                    )
                }
            },
            contentPadding = PaddingValues(8.dp)
        )
    }
}
