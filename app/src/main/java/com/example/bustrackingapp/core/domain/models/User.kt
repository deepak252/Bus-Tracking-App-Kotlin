package com.example.bustrackingapp.core.domain.models

data class User(
    val id : String,
    val name : String,
    val email : String,
    val phone : String,
    val password : String,
    val userType : String
)
