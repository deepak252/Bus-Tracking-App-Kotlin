package com.example.bustrackingapp.presentation.components.logo

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.bustrackingapp.R

@Composable
fun LogoMiniCircular(
    radius : Dp= 100.dp,
    margin: Dp  = 16.dp
) {
    Image(
        painter = painterResource(id = R.drawable.logo_mini_circular),
        contentDescription = "logo_mini",
        modifier = Modifier
            .height(radius)
            .width(radius)
            .padding(margin)
    )
}