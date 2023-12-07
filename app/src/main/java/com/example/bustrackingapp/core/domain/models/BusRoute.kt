package com.example.bustrackingapp.core.domain.models

/**
 * in BusStopWithRoutes
 */
data class BusRoute(
    val _id: String,
    val routeNo: String,
    val name: String,
//    val stops: List<Stop>,
    val schedule: List<RouteSchedule>,
    val rating: Int,
    val createdAt: String,
    val updatedAt: String
)

//data class Stop(
//    val _id: String,
//    val duration: Int,
//    val stop: String //stop Id
//)
