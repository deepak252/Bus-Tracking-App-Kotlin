package com.example.bustrackingapp.domain.repository

import com.example.bustrackingapp.common.NetworkResult
import com.example.bustrackingapp.domain.model.request.SignInUserRequestBody
import com.example.bustrackingapp.domain.model.request.SignUpUserRequestBody
import com.example.bustrackingapp.domain.model.response.SignInUserResponse
import com.example.bustrackingapp.domain.model.response.SignUpUserResponse

interface AuthRepository {
    suspend fun signInUser(requestBody : SignInUserRequestBody) : NetworkResult<SignInUserResponse>
    suspend fun signUpUser(requestBody : SignUpUserRequestBody) : NetworkResult<SignUpUserResponse>
}