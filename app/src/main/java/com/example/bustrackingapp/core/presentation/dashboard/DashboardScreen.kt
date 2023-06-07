package com.example.bustrackingapp.core.presentation.dashboard

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.bustrackingapp.core.presentation.components.BottomNavItem
import com.example.bustrackingapp.core.presentation.navigation.BottomNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun DashboardScreen(
    bottomNavController: NavHostController = rememberAnimatedNavController()
){
    Scaffold(
        bottomBar = {
            BottomNavBar(navController = bottomNavController)
        }
    ) { paddingValues->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ){
//            CurrentTab()
            BottomNavHost(navController = bottomNavController)

        }
    }
}


@Composable
private fun BottomNavBar(navController : NavHostController){
    val bottomNavItems = listOf(
        BottomNavItem.Home,
        BottomNavItem.BusRoutes,
        BottomNavItem.Profile,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(
        modifier = Modifier
//            .fillMaxHeight(.1f)
            .padding(
                horizontal = 12.dp, vertical = 12.dp
            )
            .clip(RoundedCornerShape(12.dp))
        ,
        tonalElevation = 12.dp,
    ) {
        bottomNavItems.forEach{item->
            val selected = currentDestination?.hierarchy?.any {
                it.route == item.route
            } == true
            NavigationBarItem(
                selected = selected,
                alwaysShowLabel = false,
                onClick = {
                    navController.navigate(item.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                },
                icon = {
                    if(item.drawableIcon!=null)
                        Icon(painterResource(id = item.drawableIcon!!), contentDescription = null)
                    else
                        Icon(item.icon, contentDescription = item.title)
                },
                label = { Text(text = item.title) },
            )
        }
    }

}
