package com.example.bustrackingapp.core.domain.models

data class Timing(
    val _id: String,
    val day: String,
    val departureTime: List<String>
)