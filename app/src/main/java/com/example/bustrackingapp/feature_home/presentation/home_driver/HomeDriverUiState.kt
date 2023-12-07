package com.example.bustrackingapp.feature_home.presentation.home_driver

import com.example.bustrackingapp.core.domain.models.User

data class HomeDriverUiState(
    val user : User?=null,
    val isLoading : Boolean = false,
    val isRefreshing : Boolean = false,
    val error : String?=null
)