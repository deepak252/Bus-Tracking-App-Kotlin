package com.example.bustrackingapp.core.presentation.splash

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.bustrackingapp.core.presentation.components.CustomElevatedButton
import com.example.bustrackingapp.core.presentation.components.logo.LogoCircularLight

//@Composable
//fun UserTypeSelectionScreen(
//    onSelectedPassenger : ()->Unit,
//    onSelectedDriver : ()->Unit
//) {
//
//    Scaffold(
//        modifier = Modifier
//            .clickable(
//                // Remove Ripple Effect
//                indication = null,
//                interactionSource = remember { MutableInteractionSource() }
//            ) {
//            },
//
//        ) { paddingValues ->
//        Box(
//            modifier = Modifier
//                .padding(paddingValues)
//                .fillMaxSize()
//                .verticalScroll(rememberScrollState())
//
//        ) {
//            Column(
//                modifier = Modifier
//                    .padding(horizontal = 12.dp),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ){
//                Spacer(modifier = Modifier.height(16.dp))
//                LogoCircularLight()
//                Spacer(modifier = Modifier.height(50.dp))
//                CustomElevatedButton(
//                    onClick = onSelectedPassenger,
//                    text = "I am Passenger"
//                )
//                Spacer(modifier = Modifier.height(16.dp))
//                Text(text = "OR")
//                Spacer(modifier = Modifier.height(16.dp))
//                CustomElevatedButton(
//                    onClick = onSelectedDriver,
//                    text = "I am Driver"
//                )
//                Spacer(modifier = Modifier.height(16.dp))
//            }
//
//        }
//    }
//}

