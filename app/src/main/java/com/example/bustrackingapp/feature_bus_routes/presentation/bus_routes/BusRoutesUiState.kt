package com.example.bustrackingapp.feature_bus_routes.presentation.bus_routes

import com.example.bustrackingapp.feature_bus_routes.domain.models.BusRouteWithStops
import com.example.bustrackingapp.feature_profile.domain.models.User

data class BusRoutesUiState(
    val busRoutes : List<BusRouteWithStops> = emptyList(),
    val isLoading : Boolean = false,
    val isRefreshing : Boolean = false,
    val error : String?=null
)
