package com.example.bustrackingapp.feature_bus_routes.presentation.bus_routes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
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
import com.example.bustrackingapp.core.presentation.components.CustomLoadingIndicator
import com.example.bustrackingapp.core.util.LoggerUtil
import com.example.bustrackingapp.feature_bus_routes.domain.models.BusRouteWithStops
import com.example.bustrackingapp.feature_bus_routes.presentation.components.BusRouteTile
import com.example.bustrackingapp.ui.theme.NavyBlue300
import com.example.bustrackingapp.ui.theme.Red400
import com.example.bustrackingapp.ui.theme.White
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusRoutesScreen(
    busRoutesViewModel : BusRoutesViewModel = hiltViewModel(),
    snackbarState : SnackbarHostState = remember {
        SnackbarHostState()
    },
    onBusRouteClick : (BusRouteWithStops)->Unit
){
    val logger = LoggerUtil(c = "BusRoutesScreen")
    LaunchedEffect(key1 = busRoutesViewModel.uiState.error){
        logger.info("Show Snackbar")

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
            BusRouteList(
                busRoutes = {busRoutesViewModel.uiState.busRoutes},
                isLoading = {busRoutesViewModel.uiState.isLoading},
                isRefreshing = {busRoutesViewModel.uiState.isRefreshing},
                onRefresh = busRoutesViewModel::getAllBusRoutes,
                onBusRouteClick = onBusRouteClick
            )
        }
    }
}

@Composable
private fun BusRouteList(
    busRoutes : ()->List<BusRouteWithStops>,
    isLoading : ()->Boolean,
    isRefreshing : ()->Boolean,
    onRefresh : (isLoading : Boolean, isRefreshing : Boolean)->Unit,
    onBusRouteClick : (BusRouteWithStops)->Unit
){
    if(isLoading()){
        return CustomLoadingIndicator()
    }
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = isRefreshing()),
        onRefresh = {onRefresh(false, true)},
    ) {
        LazyColumn(
            content = {
                itemsIndexed(busRoutes()){ index,item->
                    if(index==0){
                        Divider(color = NavyBlue300)
                    }
                    BusRouteTile(
                        routeNo = item.routeNo,
                        routeName = item.name,
                        onClick = {
                            onBusRouteClick(item)
                        }
                    )
                    Divider(color = NavyBlue300)
                }
            },
            contentPadding = PaddingValues(8.dp)
        )
    }

}