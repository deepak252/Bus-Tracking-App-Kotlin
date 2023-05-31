package com.example.bustrackingapp.presentation.screens.dashboard.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bustrackingapp.presentation.components.CustomElevatedButton


@Composable
fun ProfileScreen(
    profileViewModel : ProfileViewModel = hiltViewModel()
){
    Column(
        modifier = Modifier
            .fillMaxHeight()

    ) {
        Text("Profile")
        Spacer(modifier = Modifier.height(26.dp))
        CustomElevatedButton(
            onClick = profileViewModel::getUserProfile,
            text = "Fetch Profile"
        )
        Spacer(modifier = Modifier.height(6.dp))
        CustomElevatedButton(
            onClick = profileViewModel::testBlock,
            text = "Log Out"
        )
        Spacer(modifier = Modifier.height(6.dp))

    }

}