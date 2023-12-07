package com.example.bustrackingapp.core.domain.models

/**
 * in BusRouteWithStop
 */
data class BusStop(
    val _id: String,
    val stopNo: String,
    val name: String,
    val location: Location,
//    val routes: List<String>,
    val createdAt: String,
    val updatedAt: String
)