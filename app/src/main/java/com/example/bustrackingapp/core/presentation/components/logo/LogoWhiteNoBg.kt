package com.example.bustrackingapp.core.presentation.components.logo

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
fun LogoWhiteNoBg(
    width : Dp= 200.dp,
    padding: Dp  = 16.dp
) {
    Image(
        painter = painterResource(id = R.drawable.logo_white_nobg),
        contentDescription = "logo_white_nobg",
        modifier = Modifier
            .width(width)
            .padding(padding)
    )
}