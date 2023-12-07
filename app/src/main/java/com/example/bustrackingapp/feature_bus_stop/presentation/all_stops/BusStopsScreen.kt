package com.example.bustrackingapp.feature_bus_stop.presentation.all_stops

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bustrackingapp.core.presentation.components.CustomLoadingIndicator
import com.example.bustrackingapp.core.presentation.components.RefreshContainer
import com.example.bustrackingapp.core.util.LoggerUtil
import com.example.bustrackingapp.feature_bus_stop.domain.model.BusStopWithRoutes
import com.example.bustrackingapp.feature_bus_stop.presentation.components.BusStopTile
import com.example.bustrackingapp.ui.theme.NavyBlue300
import com.example.bustrackingapp.ui.theme.Red400
import com.example.bustrackingapp.ui.theme.White
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusStopsScreen(
    busStopsViewModel: BusStopsViewModel = hiltViewModel(),
    snackbarState : SnackbarHostState = remember {
        SnackbarHostState()
    },
    onStopItemClick : (String)->Unit
){
    val logger = LoggerUtil(c = "BusRoutesScreen")
    LaunchedEffect(key1 = busStopsViewModel.uiState.error){
        logger.info("Show Snackbar")

        if(busStopsViewModel.uiState.error!=null){
            snackbarState.showSnackbar(busStopsViewModel.uiState.error!!)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Bus Stops",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarState,
            ){
                if(busStopsViewModel.uiState.error!=null){
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
            BusStopList(
                busStops = {busStopsViewModel.uiState.busStops},
                isLoading = {busStopsViewModel.uiState.isLoading},
                isRefreshing = {busStopsViewModel.uiState.isRefreshing},
                onRefresh = busStopsViewModel::getAllBusStops,
                onStopItemClick = onStopItemClick
            )
        }
    }
}

@Composable
private fun BusStopList(
    busStops : ()->List<BusStopWithRoutes>,
    isLoading : ()->Boolean,
    isRefreshing : ()->Boolean,
    onRefresh : (isLoading : Boolean, isRefreshing : Boolean)->Unit,
    onStopItemClick : (String)->Unit
){
    if(isLoading()){
        return CustomLoadingIndicator()
    }

    if(busStops().isEmpty()){
        return RefreshContainer(
            modifier = Modifier.fillMaxHeight(0.8f),
            message = "No Bus Stops Found!",
            onRefresh = {
                onRefresh(false, true)
            }
        )
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = isRefreshing()),
        onRefresh = {onRefresh(false, true)},
    ) {
        LazyColumn(
            content = {
                itemsIndexed(busStops()){ index,item->
                    if(index==0){
                        Divider(color = NavyBlue300)
                    }
                    BusStopTile(
                        stopNo = item.stopNo,
                        stopName = item.name,
                        onClick = {
                            onStopItemClick(item.stopNo)
                        }
                    )
                    Divider(color = NavyBlue300)
                }
            },
            contentPadding = PaddingValues(8.dp)
        )
    }

}