package com.example.bustrackingapp.feature_auth.data.remote.dto.request

data class SignUpUserRequest(
    val name : String,
    val phone : String,
    val email : String,
    val password : String
)