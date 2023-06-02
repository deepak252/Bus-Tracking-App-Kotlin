package com.example.bustrackingapp.presentation.screens.dashboard.profile

import com.example.bustrackingapp.domain.model.User

data class ProfileUiState(
    val token : String= "",
    val user : User?=null,
    val isLoading : Boolean = false,
    val isRefreshing : Boolean = false,
    val error : String?=null
)