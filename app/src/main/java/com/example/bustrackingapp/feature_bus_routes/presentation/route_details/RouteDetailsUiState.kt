package com.example.bustrackingapp.feature_bus_routes.presentation.route_details

import com.example.bustrackingapp.core.domain.models.Bus
import com.example.bustrackingapp.feature_bus.domain.models.BusWithRoute
import com.example.bustrackingapp.feature_bus_routes.domain.models.BusRouteWithStops

data class RouteDetailsUiState(
    val route : BusRouteWithStops?=null,
    val buses : List<Bus> = emptyList(),
    val isLoading : Boolean = false,
    val isRefreshing : Boolean = false,
    val showScheduleBottomSheet : Boolean = false,
    val error : String?=null
)
