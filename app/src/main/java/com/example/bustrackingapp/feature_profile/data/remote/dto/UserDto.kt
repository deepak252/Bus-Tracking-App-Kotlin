package com.example.bustrackingapp.feature_profile.data.remote.dto

import com.example.bustrackingapp.core.domain.models.User

data class UserDto(
    val _id: String,
    val name: String,
    val email: String,
    val phone: String,
    val password: String,
    val userType : String,
    val createdAt: String,
    val updatedAt: String
)

fun UserDto.toUser() : User {
    return User(
        id = _id,
        name = name,
        email = email,
        phone = phone,
        password = password,
        userType = userType
    )
}