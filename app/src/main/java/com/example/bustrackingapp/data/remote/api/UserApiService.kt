package com.example.bustrackingapp.data.remote.api

import com.example.bustrackingapp.domain.model.ApiResponse
import com.example.bustrackingapp.domain.model.User
import retrofit2.http.GET

interface UserApiService {
    @GET("user/getUser")
    suspend fun getUser() : ApiResponse<User>
}