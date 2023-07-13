package com.example.bustrackingapp.feature_profile.domain.use_case

import com.example.bustrackingapp.core.data.remote.dto.ApiResponse
import com.example.bustrackingapp.core.util.ApiHandler
import com.example.bustrackingapp.core.util.Resource
import com.example.bustrackingapp.feature_profile.data.remote.dto.toUser
import com.example.bustrackingapp.core.domain.models.User
import com.example.bustrackingapp.feature_profile.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) : ApiHandler() {
    operator fun invoke() : Flow<Resource<User>> = makeRequest(
        apiCall = {
            val apiResponse = userRepository.getUser()
            ApiResponse(
                success = apiResponse.success,
                data = apiResponse.data.toUser(),
                message = apiResponse.message
            )
        }
    )
}