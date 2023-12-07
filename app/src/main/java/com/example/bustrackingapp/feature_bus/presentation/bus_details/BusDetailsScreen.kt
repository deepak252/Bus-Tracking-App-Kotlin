package com.example.bustrackingapp.feature_bus.presentation.bus_details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.example.bustrackingapp.core.domain.models.Bus
import com.example.bustrackingapp.core.presentation.components.CustomLoadingIndicator
import com.example.bustrackingapp.core.presentation.components.FieldValue
import com.example.bustrackingapp.ui.theme.Red400
import com.example.bustrackingapp.ui.theme.White
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusDetailsScreen(
    vehNo : String,
    busDetailsViewModel: BusDetailsViewModel = hiltViewModel(),
    snackbarState : SnackbarHostState = remember {
        SnackbarHostState()
    },
){
    LaunchedEffect(key1 = Unit){
        busDetailsViewModel.getBusDetails(vehNo,isLoading = true)
    }

    LaunchedEffect(key1 = busDetailsViewModel.uiState.error){
        if(busDetailsViewModel.uiState.error!=null){
            snackbarState.showSnackbar(busDetailsViewModel.uiState.error!!)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Bus Details",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarState,
            ){
                if(busDetailsViewModel.uiState.error!=null){
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
            BusDetailsContainer(
                isLoading = busDetailsViewModel.uiState.isLoading,
                isRefreshing = { busDetailsViewModel.uiState.isRefreshing },
                bus = busDetailsViewModel.uiState.bus,
                onRefresh = busDetailsViewModel::getBusDetails,
            )
        }
    }
}


@Composable
fun BusDetailsContainer(
    modifier : Modifier = Modifier,
    isLoading : Boolean,
    isRefreshing : ()->Boolean,
    onRefresh : (routeNo : String ,isLoading : Boolean,isRefreshing : Boolean)->Unit,
    bus : Bus?,
){
    if(isLoading){
        return CustomLoadingIndicator()
    }
    if(bus==null){
        return Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Text("Something Went Wrong!")
        }
    }

    return SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = isRefreshing()),
        onRefresh = {
//            onRefresh(busStop.stopNo,false, true)
        },
    ) {
        Column(
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            FieldValue(field = "Veh No", value = bus.vehNo )

            Spacer(modifier = Modifier.height(6.dp))
            FieldValue(field = "Type", value = bus.info.busType )

            Spacer(modifier = Modifier.height(6.dp))
            FieldValue(field = "Status", value = bus.status?:"NA" )

            Spacer(modifier = Modifier.height(24.dp))




        }
    }

}