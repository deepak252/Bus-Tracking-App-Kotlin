package com.example.bustrackingapp.domain.model

data class ApiResponse<T>(
    val success : Boolean?,
    val data : T,
    val message : String?,
//    val error : String?
)