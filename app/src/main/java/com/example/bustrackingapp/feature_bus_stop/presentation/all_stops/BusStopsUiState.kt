package com.example.bustrackingapp.feature_bus_stop.presentation.all_stops

import com.example.bustrackingapp.feature_bus_stop.domain.model.BusStopWithRoutes

data class BusStopsUiState(
    val busStops : List<BusStopWithRoutes> = emptyList(),
    val isLoading : Boolean = false,
    val isRefreshing : Boolean = false,
    val error : String?=null
)