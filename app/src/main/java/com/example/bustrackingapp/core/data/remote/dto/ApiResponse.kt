package com.example.bustrackingapp.core.data.remote.dto

data class ApiResponse<T>(
    val success : Boolean?,
    val data : T,
    val message : String?
)