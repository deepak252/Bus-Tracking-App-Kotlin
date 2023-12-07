package com.example.bustrackingapp.feature_bus.domain.use_cases

data class BusUseCases(
    val getBusByVehNo : GetBusByVehNoUseCase,
    val getAllBuses : GetAllBusesUseCase,
    val getBusesForRoute : GetBusesForRouteUseCase,
    val getBusesForStop : GetBusesForStopUseCase,
    val getNearbyBuses : GetNearbyBusesUseCase
)
