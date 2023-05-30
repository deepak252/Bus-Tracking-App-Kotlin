package com.example.bustrackingapp.data.remote.api

import com.example.bustrackingapp.domain.model.ApiResponse
import com.example.bustrackingapp.domain.model.request.SignInUserRequestBody
import com.example.bustrackingapp.domain.model.request.SignUpUserRequestBody
import com.example.bustrackingapp.domain.model.response.SignInUserResponse
import com.example.bustrackingapp.domain.model.response.SignUpUserResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("auth/signInUser")
    suspend fun signInUser(@Body requestBody : SignInUserRequestBody) : ApiResponse<SignInUserResponse>

    @POST("auth/registerUser")
    suspend fun signUpUser(@Body requestBody : SignUpUserRequestBody) : ApiResponse<SignUpUserResponse>
}