package com.example.bustrackingapp.feature_bus_routes.presentation.bus_routes

import com.example.bustrackingapp.feature_bus_routes.domain.models.BusRouteWithStops

data class BusRoutesUiState(
    val allRoutes : List<BusRouteWithStops> = emptyList(),
    val busRoutes : List<BusRouteWithStops> = emptyList(), // search result
    val searchInput : String = "",
    val isLoading : Boolean = false,
    val isRefreshing : Boolean = false,
    val error : String?=null
)
