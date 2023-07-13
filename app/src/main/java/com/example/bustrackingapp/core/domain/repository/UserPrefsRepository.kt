package com.example.bustrackingapp.core.domain.repository

import kotlinx.coroutines.flow.Flow


interface UserPrefsRepository {
    suspend fun updateToken(token : String?)
    val getToken : Flow<String>

    suspend fun updateUserType(userType : String?)
    val getUserType : Flow<String>
}