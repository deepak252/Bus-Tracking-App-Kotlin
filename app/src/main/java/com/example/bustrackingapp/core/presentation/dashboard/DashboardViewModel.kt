package com.example.bustrackingapp.core.presentation.dashboard

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.bustrackingapp.core.presentation.components.BottomNavItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor() : ViewModel(){
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.BusRoutes,
        BottomNavItem.Profile,
    )
    var selectedItem by mutableStateOf<BottomNavItem>(BottomNavItem.Home)
        private set

    fun onItemClick(item : BottomNavItem){
        selectedItem = item
    }
}