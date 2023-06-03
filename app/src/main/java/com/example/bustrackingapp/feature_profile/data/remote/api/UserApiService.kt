package com.example.bustrackingapp.feature_profile.data.remote.api

import com.example.bustrackingapp.core.data.remote.dto.ApiResponse
import com.example.bustrackingapp.feature_profile.data.remote.dto.UserDto
import retrofit2.http.GET

interface UserApiService {
    @GET("user/getUser")
    suspend fun getUser() : ApiResponse<UserDto>
}