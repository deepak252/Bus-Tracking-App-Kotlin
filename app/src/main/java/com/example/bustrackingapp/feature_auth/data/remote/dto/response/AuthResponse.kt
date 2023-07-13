package com.example.bustrackingapp.feature_auth.data.remote.dto.response

import com.example.bustrackingapp.core.domain.models.User

data class AuthResponse(
    val user : User,
    val token : String
)