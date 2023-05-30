package com.example.bustrackingapp.domain.model.response

import com.example.bustrackingapp.domain.model.User

data class SignInUserResponse(
    val user : User,
    val token : String
)