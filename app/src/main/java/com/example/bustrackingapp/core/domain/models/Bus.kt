package com.example.bustrackingapp.core.domain.models

data class Bus(
    val _id: String,
    val vehNo: String,
    val status: String?,
    val info: BusInfo,
    var location: Location?,
    val route: String?,
    val createdAt: String,
    val updatedAt: String,
)

data class BusInfo(
    val _id: String,
    val busType: String
)