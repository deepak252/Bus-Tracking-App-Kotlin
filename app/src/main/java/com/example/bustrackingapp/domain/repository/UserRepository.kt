package com.example.bustrackingapp.domain.repository

import com.example.bustrackingapp.common.NetworkResult
import com.example.bustrackingapp.domain.model.User


interface UserRepository {
    suspend fun getUser() : NetworkResult<User>
}