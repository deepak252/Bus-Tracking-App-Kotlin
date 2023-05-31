package com.example.bustrackingapp.domain.repository

import kotlinx.coroutines.flow.Flow


interface UserPrefsRepository {
    suspend fun updateToken(token : String?)
    val getToken : Flow<String>
}