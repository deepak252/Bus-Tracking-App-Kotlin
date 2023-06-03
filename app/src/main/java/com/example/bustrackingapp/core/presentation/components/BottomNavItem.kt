package com.example.bustrackingapp.core.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.bustrackingapp.core.presentation.navigation.BottomNavRoutes

sealed class BottomNavItem(
    var route : String,
    var title : String,
    var selectedIcon : ImageVector,
    var icon : ImageVector,
) {
    object Home : BottomNavItem(
        BottomNavRoutes.HomeScreen.route,
        "Home",
        Icons.Filled.Home,
        Icons.Outlined.Home,
    )
    object BusRoutes : BottomNavItem(
        BottomNavRoutes.BusRoutesScreen.route,
        "Favorites",
        Icons.Filled.Favorite,
        Icons.Outlined.FavoriteBorder,
    )
    object Profile : BottomNavItem(
        BottomNavRoutes.ProfileScreen.route,
        "Profile",
        Icons.Filled.Person,
        Icons.Outlined.Person,
    )
}