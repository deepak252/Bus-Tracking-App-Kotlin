package com.example.bustrackingapp.feature_bus_stop.presentation.stop_details

import com.example.bustrackingapp.feature_bus.domain.models.BusWithRoute
import com.example.bustrackingapp.feature_bus_routes.domain.models.BusRouteWithStops
import com.example.bustrackingapp.feature_bus_stop.domain.model.BusStopWithRoutes

data class StopDetailsUiState(
    val stop : BusStopWithRoutes?=null,
    val buses : List<BusWithRoute> = emptyList(),
    val isLoading : Boolean = false,
    val isRefreshing : Boolean = false,
    val error : String?=null
)