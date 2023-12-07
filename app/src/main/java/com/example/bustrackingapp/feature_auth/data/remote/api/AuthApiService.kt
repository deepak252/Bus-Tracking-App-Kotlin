package com.example.bustrackingapp.feature_auth.data.remote.api

import com.example.bustrackingapp.core.data.remote.dto.ApiResponse
import com.example.bustrackingapp.feature_auth.data.remote.dto.response.AuthResponse
import com.example.bustrackingapp.feature_auth.data.remote.dto.request.SignInUserRequest
import com.example.bustrackingapp.feature_auth.data.remote.dto.request.SignUpUserRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("auth/signInUser")
    suspend fun signInUser(@Body requestBody : SignInUserRequest) : ApiResponse<AuthResponse>

    @POST("auth/registerUser")
    suspend fun signUpUser(@Body requestBody : SignUpUserRequest) : ApiResponse<AuthResponse>
}