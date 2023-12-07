package com.example.bustrackingapp.feature_bus_routes.presentation.route_details

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.min
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bustrackingapp.R
import com.example.bustrackingapp.core.domain.models.Bus
import com.example.bustrackingapp.core.presentation.components.CustomLoadingIndicator
import com.example.bustrackingapp.core.presentation.components.CustomOutlinedButton
import com.example.bustrackingapp.core.presentation.components.CustomTimeline
import com.example.bustrackingapp.core.presentation.components.FieldValue
import com.example.bustrackingapp.core.presentation.components.TimelineItem
import com.example.bustrackingapp.core.util.BusRouteUtil
import com.example.bustrackingapp.core.util.DateTimeUtil
import com.example.bustrackingapp.feature_bus_routes.domain.models.BusRouteWithStops
import com.example.bustrackingapp.feature_bus_routes.presentation.components.RouteMapView
import com.example.bustrackingapp.feature_bus_routes.presentation.components.RouteScheduleBottomSheet
import com.example.bustrackingapp.ui.theme.Blue500
import com.example.bustrackingapp.ui.theme.Blue700
import com.example.bustrackingapp.ui.theme.NavyBlue700
import com.example.bustrackingapp.ui.theme.NavyBlue900
import com.example.bustrackingapp.ui.theme.Red400
import com.example.bustrackingapp.ui.theme.White
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RouteDetailsScreen(
    routeNo : String,
    routeDetailsViewModel: RouteDetailsViewModel = hiltViewModel(),
    snackbarState : SnackbarHostState = remember {
        SnackbarHostState()
    },
){
    LaunchedEffect(key1 = Unit){
        routeDetailsViewModel.getBusRouteDetails(routeNo,isLoading = true)
        routeDetailsViewModel.connectSocket(routeNo)
    }

    LaunchedEffect(key1 = routeDetailsViewModel.uiState.error){
        if(routeDetailsViewModel.uiState.error!=null){
            snackbarState.showSnackbar(routeDetailsViewModel.uiState.error!!)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Route Details",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarState,
            ){
                if(routeDetailsViewModel.uiState.error!=null){
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
            BusRouteDetailsContainer(
                isLoading = routeDetailsViewModel.uiState.isLoading,
                isRefreshing = { routeDetailsViewModel.uiState.isRefreshing },
                busRoute = routeDetailsViewModel.uiState.route,
                onRefresh = routeDetailsViewModel::getBusRouteDetails,
                liveBuses = { routeDetailsViewModel.uiState.buses },
                onClickSchedule = routeDetailsViewModel::toggleScheduleBottomSheet
            )
        }
    }

    if(routeDetailsViewModel.uiState.showScheduleBottomSheet && routeDetailsViewModel.uiState.route!=null){
        RouteScheduleBottomSheet(
            onDismissRequest = routeDetailsViewModel::toggleScheduleBottomSheet,
            route = {routeDetailsViewModel.uiState.route!!}
        )
    }
}


@Composable
fun BusRouteDetailsContainer(
    modifier : Modifier = Modifier,
    isLoading : Boolean,
    isRefreshing : ()->Boolean,
    onRefresh : (routeNo : String ,isLoading : Boolean,isRefreshing : Boolean)->Unit,
    busRoute : BusRouteWithStops?,
    liveBuses : ()-> List<Bus>,
    onClickSchedule : ()->Unit
){


    if(isLoading){
        return CustomLoadingIndicator()
    }
    if(busRoute==null){
        return Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Text("Something Went Wrong!")
        }
    }
    val scrollState = rememberScrollState()
    val nextArrival by remember {
        mutableStateOf(BusRouteUtil.getNextArrival(busRoute))
    }

    return Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
        ){
            RouteMapView(
                busStops = busRoute.stops,
                liveBuses = liveBuses()
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.53f)
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp))
                .background(MaterialTheme.colorScheme.background)
                .padding(
                    start = 14.dp, end = 14.dp, top = 12.dp
                )

        ){
            Column(
                modifier =  modifier
                        .verticalScroll(scrollState)

            ) {
                Spacer(modifier = Modifier.height(12.dp))
                FieldValue(field = "Route No", value = busRoute.routeNo )
                Spacer(modifier = Modifier.height(6.dp))
                FieldValue(field = "Route Name", value = busRoute.name )
                Spacer(modifier = Modifier.height(12.dp))
                CustomOutlinedButton(
                    onClick = onClickSchedule
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_calendar_month_24),
                        contentDescription = null,
                        tint = Blue500
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "View Schedule",
                        color = Blue500,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Divider()
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    "Bus Stops :- ",
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.height(12.dp))
                CustomTimeline(
                    items = busRoute.stops.mapIndexed{ i, item->
                        TimelineItem(
                            item.stop.name,
                            listOf(
                                "Stop No.:  ${item.stop.stopNo}",
                                "Next Arrival:  ${nextArrival[i]}"
                            )
                        )
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }

}
