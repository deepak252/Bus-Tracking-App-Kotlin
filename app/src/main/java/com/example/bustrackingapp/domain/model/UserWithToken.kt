package com.example.bustrackingapp.domain.model

data class UserWithToken(
    val user : User,
    val token : String
)