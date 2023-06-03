package com.example.bustrackingapp.core.domain.models

data class Location(
    val id: String,
    val lat: Double,
    val lng: Double,
    val address: String?=null
)