package com.example.bustrackingapp.core.presentation.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable


import android.util.Log
import androidx.compose.foundation.Indication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.bustrackingapp.core.presentation.components.CustomElevatedButton
import com.example.bustrackingapp.core.presentation.components.CustomTextField


@Composable
fun HomeScreen(){
    var hideKeyboard by rememberSaveable{ mutableStateOf(false) }
    var text by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .clickable(
                // Remove Ripple Effect
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ){
                hideKeyboard=true
            }
    ) {
        Text("Home")
        CustomElevatedButton(
            onClick = {

            },
            text = "Click"
        )
        Spacer(modifier = Modifier.height(6.dp))
//        CustomTextField(
//            value = text,
//            onValueChange = {
//                text=it
//            },
//
//            hideKeyboard = hideKeyboard,
//            hintText = "Enter text",
//            labelText = "Custom Text",
//            onFocusClear = {
//                hideKeyboard=false
//            }
//
//        )
    }

}