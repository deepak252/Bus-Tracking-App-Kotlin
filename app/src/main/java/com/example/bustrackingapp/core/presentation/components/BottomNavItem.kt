package com.example.bustrackingapp.core.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.bustrackingapp.R
import com.example.bustrackingapp.core.presentation.navigation.BottomNavRoutes

sealed class BottomNavItem(
    var route : String,
    var title : String,
    var icon : ImageVector,
    var drawableIcon : Int?=null
) {
    object Home : BottomNavItem(
        BottomNavRoutes.HomeScreen.route,
        "Home",
        Icons.Outlined.Home,
    )
    object BusRoutes : BottomNavItem(
        BottomNavRoutes.BusRoutesScreen.route,
        "Bus Routes",
        Icons.Outlined.FavoriteBorder,
        R.drawable.outline_directions_bus_24
    )
    object Profile : BottomNavItem(
        BottomNavRoutes.ProfileScreen.route,
        "Profile",
        Icons.Outlined.Person,
    )
}