package com.example.bustrackingapp.core.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.bustrackingapp.R

sealed class BottomNavItem(
    var title : String,
    var icon : ImageVector,
    var drawableIcon : Int?=null
) {
    object Home : BottomNavItem(
        "Home",
        Icons.Outlined.Home,
    )
    object BusRoutes : BottomNavItem(
        "Bus Routes",
        Icons.Outlined.FavoriteBorder,
        R.drawable.outline_directions_bus_24
    )
    object Profile : BottomNavItem(
        "Profile",
        Icons.Outlined.Person,
    )
}