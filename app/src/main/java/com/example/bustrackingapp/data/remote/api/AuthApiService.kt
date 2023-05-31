package com.example.bustrackingapp.data.remote.api

import com.example.bustrackingapp.domain.model.ApiResponse
import com.example.bustrackingapp.domain.model.UserWithToken
import com.example.bustrackingapp.domain.model.request.SignInUserRequestBody
import com.example.bustrackingapp.domain.model.request.SignUpUserRequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("auth/signInUser")
    suspend fun signInUser(@Body requestBody : SignInUserRequestBody) : ApiResponse<UserWithToken>

    @POST("auth/registerUser")
    suspend fun signUpUser(@Body requestBody : SignUpUserRequestBody) : ApiResponse<UserWithToken>
}