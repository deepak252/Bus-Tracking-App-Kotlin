package com.example.bustrackingapp.core.domain.models

data class RouteSchedule(
    val _id: String,
    val day: String,
    val departureTime: List<String>
)