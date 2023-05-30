package com.example.bustrackingapp.domain.model.request

data class SignUpUserRequestBody(
    val name : String,
    val phone : String,
    val email : String,
    val password : String
)