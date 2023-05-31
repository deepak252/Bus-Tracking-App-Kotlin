package com.example.bustrackingapp.domain.repository

import com.example.bustrackingapp.common.NetworkResult
import com.example.bustrackingapp.domain.model.UserWithToken
import com.example.bustrackingapp.domain.model.request.SignInUserRequestBody
import com.example.bustrackingapp.domain.model.request.SignUpUserRequestBody

interface AuthRepository {
    suspend fun signInUser(requestBody : SignInUserRequestBody) : NetworkResult<UserWithToken>
    suspend fun signUpUser(requestBody : SignUpUserRequestBody) : NetworkResult<UserWithToken>
}